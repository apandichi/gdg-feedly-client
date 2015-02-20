package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OauthResponseActivity extends ActionBarActivity {

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";

    private String accessToken;
    private String refreshToken;

    private FeedlyRetrofitClient feedlyRetrofitClient;
    private FeedlyService feedlyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        accessToken = savedInstanceState.getString(ACCESS_TOKEN);
        refreshToken = savedInstanceState.getString(REFRESH_TOKEN);
        setContentView(R.layout.activity_oauth_response);

        feedlyRetrofitClient = new FeedlyRetrofitClient(accessToken);
        feedlyService = feedlyRetrofitClient.getFeedlyService();
        //requestCategories();

        requestCategories();
    }

    private void requestCategories() {
        feedlyService.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                System.out.println(categories);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oauth_response, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, OAuthAccessTokenResponse oAuthAccessTokenResponse) {
        Intent intent = new Intent(context, OauthResponseActivity.class);
        intent.putExtra(ACCESS_TOKEN, oAuthAccessTokenResponse.getAccessToken());
        intent.putExtra(REFRESH_TOKEN, oAuthAccessTokenResponse.getRefreshToken());
        return intent;
    }
}
