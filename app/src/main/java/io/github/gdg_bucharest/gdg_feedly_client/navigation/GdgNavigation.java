package io.github.gdg_bucharest.gdg_feedly_client.navigation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.R;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Count;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.MarkersCounts;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;

/**
 * Created by pndl on 2/28/15.
 */
public class GdgNavigation extends BaseExpandableListAdapter {

    private Context context;

    private HashMap<String, GdgCategory> categories = new HashMap<>();
    private HashMap<String, GdgSubscription> subscriptions = new HashMap<>();

    public GdgNavigation(Context context) {
        this.context = context;
    }

    public void loadCategories(List<Category> categoryList) {
        HashMap<String, GdgCategory> categoriesMap = new HashMap<>();
        for (Category category : categoryList) {
            GdgCategory gdgCategory = new GdgCategory(category);
            categoriesMap.put(category.id, gdgCategory);
        }
        this.categories = categoriesMap;
    }

    public void loadSubscriptions(List<Subscription> subscriptionList) {
        HashMap<String, GdgSubscription> subscriptionsMap = new HashMap<>();
        for (Subscription subscription : subscriptionList) {
            GdgSubscription gdgSubscription = new GdgSubscription(subscription);
            subscriptionsMap.put(subscription.getId(), gdgSubscription);
            putSubscriptionInCategories(gdgSubscription);
        }
        this.subscriptions = subscriptionsMap;
    }

    private void putSubscriptionInCategories(GdgSubscription gdgSubscription) {
        for (Category category : gdgSubscription.getSubscription().getCategories()) {
            GdgCategory targetCategory = categories.get(category.id);
            targetCategory.addSubscription(gdgSubscription);
        }
    }

    public void loadMarkersCounts(MarkersCounts markersCounts) {
        for (Count count : markersCounts.unreadcounts) {
            GdgCount gdgCount = new GdgCount(count);
            setupCount(gdgCount);
        }
    }

    private void setupCount(GdgCount gdgCount) {
        if (gdgCount.getCountType() == GdgCount.CountType.USER_CATEGORY) {
            String categoryId = gdgCount.getCount().getId();
            GdgCategory category = categories.get(categoryId);
            // some categories omitted, such as global.uncategorized
            if (category != null) {
                category.setUnreadCount(gdgCount.getCount().getCount());
            }
        } else if (gdgCount.getCountType() == GdgCount.CountType.FEED) {
            String subscriptionId = gdgCount.getCount().getId();
            GdgSubscription subscription = subscriptions.get(subscriptionId);
            subscription.setUnreadCount(gdgCount.getCount().getCount());
        }
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).getSubscriptions().size();
    }

    @Override
    public GdgCategory getGroup(int groupPosition) {
        return categories.values().toArray(new GdgCategory[]{})[groupPosition];
    }

    @Override
    public GdgSubscription getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getSubscriptions().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_item_primary, null);
        }
        GdgCategory group = getGroup(groupPosition);
        TextView textView = (TextView) convertView.findViewById(R.id.name);
        textView.setText(group.getCategory().label);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_item_secondary, null);
        }
        GdgSubscription child = getChild(groupPosition, childPosition);
        TextView textView = (TextView) convertView.findViewById(R.id.name);
        textView.setText(child.getSubscription().getTitle());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
