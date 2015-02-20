package io.github.gdg_bucharest.gdg_feedly_client;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by pndl on 2/16/15.
 */
public interface FeedlyService {

    @GET("/categories")
    public void getCategories(Callback<List<Category>> callback);
}
