package io.github.gdg_bucharest.gdg_feedly_client.navigation;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

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
public class GdgNavigation {

    private HashMap<String, GdgCategory> categories = new HashMap<>();
    private HashMap<String, GdgSubscription> subscriptions = new HashMap<>();

    private List<GdgCategory> getGdgCategories() {
        return new ArrayList<>(categories.values());
    }

    public IDrawerItem[] getCategoryItems() {
        List<IDrawerItem> items = new ArrayList<>();
        for (GdgCategory gdgCategory : categories.values()) {
            addCategoryItem(items, gdgCategory);
            addSubscriptionItems(items, gdgCategory);
        }
        return items.toArray(new IDrawerItem[]{});
    }

    private void addSubscriptionItems(List<IDrawerItem> items, GdgCategory gdgCategory) {
        for (GdgSubscription gdgSubscription : gdgCategory.getSubscriptions()) {
            SecondaryDrawerItem secondaryDrawerItem = new SecondaryDrawerItem()
                    .withName(gdgSubscription.getSubscription().getTitle())
                    .withBadge(gdgSubscription.getUnreadCount().toString());
            //.withIdentifier()
            items.add(secondaryDrawerItem);
        }
    }

    private void addCategoryItem(List<IDrawerItem> items, GdgCategory gdgCategory) {
        PrimaryDrawerItem primaryDrawerItem = new PrimaryDrawerItem()
            .withName(gdgCategory.getCategory().label)
            .withBadge(gdgCategory.getUnreadCount().toString())
            .withIcon(R.mipmap.ic_home);
        //.withIdentifier()
        items.add(primaryDrawerItem);
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
}
