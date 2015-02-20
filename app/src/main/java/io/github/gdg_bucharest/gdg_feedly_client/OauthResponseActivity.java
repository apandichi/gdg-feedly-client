package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OauthResponseActivity extends ActionBarActivity {

    private FeedlyService feedlyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_response);
        feedlyService = new FeedlyServiceProvider(this).getFeedlyService();
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
}
