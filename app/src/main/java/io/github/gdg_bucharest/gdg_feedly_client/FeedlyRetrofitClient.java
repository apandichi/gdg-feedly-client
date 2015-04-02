package io.github.gdg_bucharest.gdg_feedly_client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by pndl on 2/16/15.
 */
public class FeedlyRetrofitClient {

    public static final String ENDPOINT = "https://sandbox.feedly.com/v3";
    private String accessToken;

    private RestAdapter restAdapter;

    // https://github.com/UweTrottmann/getglue-java
    public FeedlyRetrofitClient(String accessToken) {
        this.accessToken = accessToken;
    }

    public RestAdapter getRestAdapter() {
        if (restAdapter != null) return restAdapter;

        RestAdapter.Builder builder = new RestAdapter.Builder();
        RestAdapter restAdapter = builder.setEndpoint(ENDPOINT)
                .setConverter(new GsonConverter(gson()))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
//                        request.addQueryParam("access_token", accessToken);
                        request.addHeader("Authorization", "Bearer " + accessToken);
                    }
                })
            .build();
        this.restAdapter = restAdapter;
        return restAdapter;
    }

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, dateJsonSerializer())
                .registerTypeAdapter(Date.class, dateJsonDeserializer())
                .create();
    }

    private JsonDeserializer<Date> dateJsonDeserializer() {
        return new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : new Date(json.getAsLong());
            }
        };
    }

    private JsonSerializer<Date> dateJsonSerializer() {
        return new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };
    }

    public FeedlyService getFeedlyService() {
        return getRestAdapter().create(FeedlyService.class);
    }
}
