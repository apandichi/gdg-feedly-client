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

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.MarkersCounts;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Profile;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;
import io.github.gdg_bucharest.gdg_feedly_client.navigation.GdgNavigation;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HomeActivity extends ActionBarActivity {

    private FeedlyService feedlyService;
    private ListView categoriesListView;

    private GdgNavigation gdgNavigation;
    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        gdgNavigation = new GdgNavigation(this);

        categoriesListView = (ListView) findViewById(R.id.categories);
        feedlyService = new FeedlyServiceProvider(this).getFeedlyService();

        setupSlidingMenu();
        requestCategories();
        requestProfile();
    }

    private void requestProfile() {
        feedlyService.getProfile(new Callback<Profile>() {
            @Override
            public void success(Profile profile, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void setupSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.setMenu(R.layout.drawer);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);


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
                // http://stackoverflow.com/questions/20916692/expandablelistview-drill-down-in-sliding-menu
                ExpandableListView listView = (ExpandableListView) menu.getMenu().findViewById(R.id.left_drawer);
                listView.setAdapter(gdgNavigation);
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
