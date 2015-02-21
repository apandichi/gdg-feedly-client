package io.github.gdg_bucharest.gdg_feedly_client;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CategoriesActivity extends ActionBarActivity {

    private FeedlyService feedlyService;
    private ListView categoriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        categoriesListView = (ListView) findViewById(R.id.categories);
        feedlyService = new FeedlyServiceProvider(this).getFeedlyService();
        //requestCategories();


        Drawer.Result drawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withDrawerWidthDp(200)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Item1").withBadge("3").withIcon(R.mipmap.ic_home).withIdentifier(3)
                ).build();
    }

    private void requestCategories() {
        feedlyService.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                CategoriesAdapter adapter = new CategoriesAdapter(CategoriesActivity.this, categories);
                categoriesListView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }

    /*@Override
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
    }*/
}
