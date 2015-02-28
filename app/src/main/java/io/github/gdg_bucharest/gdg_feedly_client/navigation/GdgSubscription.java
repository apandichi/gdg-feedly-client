package io.github.gdg_bucharest.gdg_feedly_client.navigation;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Subscription;

/**
 * Created by pndl on 2/28/15.
 */
public class GdgSubscription {

    private Subscription subscription;
    private Integer unreadCount;

    public GdgSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }
}
