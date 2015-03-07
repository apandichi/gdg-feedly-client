package io.github.gdg_bucharest.gdg_feedly_client;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

//import com.mikepenz.materialdrawer.adapter.BaseDrawerAdapter;
import com.mikepenz.materialdrawer.adapter.DrawerAdapter;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.navigation.GdgCategory;
import io.github.gdg_bucharest.gdg_feedly_client.navigation.GdgNavigation;
import io.github.gdg_bucharest.gdg_feedly_client.navigation.GdgSubscription;

/**
 * Created by pndl on 3/3/15.
 */
public class GdgDrawerAdapter extends DrawerAdapter {


    public GdgDrawerAdapter(Activity activity) {
        super(activity);
    }

    public GdgDrawerAdapter(Activity activity, ArrayList<IDrawerItem> drawerItems) {
        super(activity, drawerItems);
    }
}
