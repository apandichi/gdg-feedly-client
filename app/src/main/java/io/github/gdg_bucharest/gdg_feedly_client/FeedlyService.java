package io.github.gdg_bucharest.gdg_feedly_client;

import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.MarkersCounts;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Profile;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.StreamContents;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by pndl on 2/16/15.
 */
public interface FeedlyService {

    @GET("/profile")
    public void getProfile(Callback<Profile> callback);

    @GET("/streams/contents")
    public void getStreamContents(@Query("streamId") String streamId, Callback<StreamContents> callback);

    @GET("/categories")
    public void getCategories(Callback<List<Category>> callback);

    @GET("/subscriptions")
    public void getSubscriptions(Callback<List<Subscription>> callback);

    @GET("/markers/counts")
    public void getMarkersCounts(Callback<MarkersCounts> callback);
}
