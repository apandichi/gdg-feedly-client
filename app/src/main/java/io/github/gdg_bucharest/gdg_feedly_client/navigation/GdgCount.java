package io.github.gdg_bucharest.gdg_feedly_client.navigation;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Count;

/**
 * Created by pndl on 2/28/15.
 */
public class GdgCount {

    private Count count;
    private CountType countType;

    public GdgCount(Count count) {
        this.count = count;
        determineCountType();
    }

    private void determineCountType() {
        if (count.getId().startsWith("feed/")) {
            countType = CountType.FEED;
        } else if (count.getId().startsWith("user/") && count.getId().contains("/category/")) {
            countType = CountType.USER_CATEGORY;
        }
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public CountType getCountType() {
        return countType;
    }

    public void setCountType(CountType countType) {
        this.countType = countType;
    }

    public enum CountType {
        FEED,
        USER_CATEGORY
    }
}
