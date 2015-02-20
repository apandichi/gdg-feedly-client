package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Context;

/**
 * Created by pndl on 2/20/15.
 */
public class FeedlyServiceProvider {

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";

    public static final String PREFS = "prefs";

    private Context context;

    public FeedlyServiceProvider(Context context) {
        this.context = context;
    }

    public void setAccessToken(String accessToken) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(ACCESS_TOKEN, accessToken)
            .commit();
    }

    public void setRefreshToken(String refreshToken) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(REFRESH_TOKEN, refreshToken)
                .commit();
    }

    public FeedlyService getFeedlyService() {
        return new FeedlyRetrofitClient(getAccessToken()).getFeedlyService();
    }

    public String getAccessToken() {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(ACCESS_TOKEN, null);
    }
}
