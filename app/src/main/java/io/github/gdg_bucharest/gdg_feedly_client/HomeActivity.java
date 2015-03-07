package io.github.gdg_bucharest.gdg_feedly_client;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
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
//    Drawer.Result drawer;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        categoriesListView = (ListView) findViewById(R.id.categories);
        feedlyService = new FeedlyServiceProvider(this).getFeedlyService();


//        setupMaterialDrawer();
        setupDrawer();

        requestCategories();
    }

    private void setupMaterialDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawer.Result drawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
//                .withListView(listView)
//                .withAdapter(adapter)
                .withActionBarDrawerToggle(true)
                .withDrawerWidthDp(250)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home")
                                .withIcon(R.mipmap.ic_home),
                        new DividerDrawerItem()
                )
                .build();

        drawer.openDrawer();
    }

    private void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        String[] mPlanetTitles = new String[] {"a", "b", "c"};
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item,
                R.id.title,
                mPlanetTitles));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout.setDrawerListener(new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        });
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
//                drawer.addItems(gdgNavigation.getCategoryItems());
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
