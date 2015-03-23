package io.github.gdg_bucharest.gdg_feedly_client.navigation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, GdgCategory> categories = new HashMap<>();
    private Map<String, GdgSubscription> subscriptions = new HashMap<>();

    private Map<String, GdgCategory> categoriesFiltered = new HashMap<>();
    private Map<String, GdgSubscription> subscriptionsFiltered = new HashMap<>();

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
        return categoriesFiltered.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<GdgSubscription> gdgSubscriptions = getGroup(groupPosition).getSubscriptions();
        return filterSubscriptions(gdgSubscriptions).size();
    }

    @Override
    public GdgCategory getGroup(int groupPosition) {
        return categoriesFiltered.values().toArray(new GdgCategory[]{})[groupPosition];
    }

    @Override
    public GdgSubscription getChild(int groupPosition, int childPosition) {
        List<GdgSubscription> gdgSubscriptions = getGroup(groupPosition).getSubscriptions();
        return filterSubscriptions(gdgSubscriptions).toArray(new GdgSubscription[] {})[childPosition];
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

        textView = (TextView) convertView.findViewById(R.id.badge);
        textView.setText(group.getUnreadCount().toString());
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

        textView = (TextView) convertView.findViewById(R.id.badge);
        textView.setText(child.getUnreadCount().toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterCategories() {
        categoriesFiltered = Maps.filterValues(categories, new Predicate<GdgCategory>() {
            @Override
            public boolean apply(GdgCategory input) {
                return input.getUnreadCount() != 0;
            }
        });
    }

    private java.util.Collection<GdgSubscription> filterSubscriptions(List<GdgSubscription> gdgSubscriptions) {
        return Collections2.filter(gdgSubscriptions, new Predicate<GdgSubscription>() {
            @Override
            public boolean apply(GdgSubscription input) {
                return input.getUnreadCount() != 0;
            }
        });
    }
}
