package io.github.gdg_bucharest.gdg_feedly_client;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.MarkersCounts;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;
import io.github.gdg_bucharest.gdg_feedly_client.navigation.GdgNavigation;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HomeActivity extends ActionBarActivity {

    private FeedlyService feedlyService;
    private ListView categoriesListView;

    GdgNavigation gdgNavigation = new GdgNavigation();
    Drawer.Result drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        categoriesListView = (ListView) findViewById(R.id.categories);
        feedlyService = new FeedlyServiceProvider(this).getFeedlyService();

        drawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withDrawerWidthDp(200)
                .addDrawerItems(
                    new PrimaryDrawerItem().withName("Home")
                        .withIcon(R.mipmap.ic_home)
                )
                .build();

        drawer.openDrawer();

        requestCategories();
    }

    private void requestCategories() {
        feedlyService.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                gdgNavigation.loadCategories(categories);
                requestSubscriptions();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }

    private void requestMarkersCount() {
        feedlyService.getMarkersCounts(new Callback<MarkersCounts>() {
            @Override
            public void success(MarkersCounts markersCounts, Response response) {
                gdgNavigation.loadMarkersCounts(markersCounts);
                drawer.addItems(gdgNavigation.getCategoryItems());
            }

            @Override
            public void failure(RetrofitError error) {
		System.out.println(error);
            }
        });
    }

    private void requestSubscriptions() {
        feedlyService.getSubscriptions(new Callback<List<Subscription>>() {
            @Override
            public void success(List<Subscription> subscriptions, Response response) {
                gdgNavigation.loadSubscriptions(subscriptions);
                requestMarkersCount();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }
}
