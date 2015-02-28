package io.github.gdg_bucharest.gdg_feedly_client.navigation;

import java.util.ArrayList;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;

/**
 * Created by pndl on 2/28/15.
 */
public class GdgCategory {

    private Category category;
    private Integer unreadCount;
    private List<GdgSubscription> subscriptions = new ArrayList<>();


    public GdgCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public List<GdgSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<GdgSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void addSubscription(GdgSubscription gdgSubscription) {
        subscriptions.add(gdgSubscription);
    }
}
