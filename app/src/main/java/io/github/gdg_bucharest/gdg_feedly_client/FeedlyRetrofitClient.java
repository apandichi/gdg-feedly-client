package io.github.gdg_bucharest.gdg_feedly_client;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

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

    public FeedlyService getFeedlyService() {
        return getRestAdapter().create(FeedlyService.class);
    }
}
