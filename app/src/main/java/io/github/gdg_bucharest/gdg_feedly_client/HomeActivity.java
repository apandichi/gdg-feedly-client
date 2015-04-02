package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Entry;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.MarkersCounts;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Profile;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.StreamContents;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;
import io.github.gdg_bucharest.gdg_feedly_client.navigation.GdgNavigation;
import io.github.gdg_bucharest.gdg_feedly_client.navigation.GdgSubscription;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HomeActivity extends ActionBarActivity {

    private FeedlyService feedlyService;
    private ListView entriesListView;

    private GdgNavigation gdgNavigation;
    private SlidingMenu menu;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gdgNavigation = new GdgNavigation(this);

        entriesListView = (ListView) findViewById(R.id.entries);
        loading = (ProgressBar) findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        feedlyService = new FeedlyServiceProvider(this).getFeedlyService();

        setupSlidingMenu();
        requestCategories();
        requestProfile();
    }

    private void requestProfile() {
        feedlyService.getProfile(new Callback<Profile>() {
            @Override
            public void success(Profile profile, Response response) {
                requestGlobalContents(profile.getId());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }

    private void requestGlobalContents(String userId) {
        String globalCategoryId = "user/{userId}/category/global.all".replace("{userId}", userId);
        requestStreamContents(globalCategoryId);
    }

    private void requestStreamContents(String streamId) {
        loading.setVisibility(View.VISIBLE);
        EntryAdapter adapter = new EntryAdapter(HomeActivity.this, new ArrayList<Entry>());
        entriesListView.setAdapter(adapter);
        boolean unreadOnly = true;
        feedlyService.getStreamContents(streamId, unreadOnly, new Callback<StreamContents>() {
            @Override
            public void success(final StreamContents streamContents, Response response) {
                EntryAdapter adapter = new EntryAdapter(HomeActivity.this, streamContents.getItems());
                entriesListView.setAdapter(adapter);
                entriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = EntryActivity.newIntent(HomeActivity.this, streamContents.getItems(), position);
                        startActivity(intent);
                    }
                });
                loading.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
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
                configureGdgNavigation();
            }

            @Override
            public void failure(RetrofitError error) {
		System.out.println(error);
            }
        });
    }

    private void configureGdgNavigation() {
        gdgNavigation.filterCategories();
        final ExpandableListView listView = (ExpandableListView) menu.getMenu().findViewById(R.id.left_drawer);
        listView.setAdapter(gdgNavigation);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                GdgSubscription gdgSubscription = gdgNavigation.getChild(groupPosition, childPosition);
                String subscriptionId = gdgSubscription.getSubscription().getId();
                requestStreamContents(subscriptionId);
                menu.showContent();
                return true;
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
