package io.github.gdg_bucharest.gdg_feedly_client;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SubscriptionsActivity extends ActionBarActivity {

    private FeedlyService feedlyService;
    private ListView subscriptionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        subscriptionsListView = (ListView) findViewById(R.id.categories);
        feedlyService = new FeedlyServiceProvider(this).getFeedlyService();

        requestSubscriptions();
    }

    private void requestSubscriptions() {
        feedlyService.getSubscriptions(new Callback<List<Subscription>>() {
            @Override
            public void success(List<Subscription> list, Response response) {
                System.out.println(list);
                SubscriptionAdapter adapter = new SubscriptionAdapter(SubscriptionsActivity.this, list);
                subscriptionsListView.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
