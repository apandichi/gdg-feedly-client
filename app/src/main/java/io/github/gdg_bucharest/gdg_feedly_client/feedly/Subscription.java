package io.github.gdg_bucharest.gdg_feedly_client.feedly;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;

/**
 * Created by pndl on 2/21/15.
 */
public class Subscription {

    private String id;
    private String title;
    private String website;
    private List<Category> categories;
    private Long updated;
    private Double subscribers;
    private Double velocity;

    private String iconUrl;
    private String visualUrl;
    private String coverUrl;

    private String coverColor;
    private List<String> topics;
    private String state;

    public Subscription() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Double getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Double subscribers) {
        this.subscribers = subscribers;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getVisualUrl() {
        return visualUrl;
    }

    public void setVisualUrl(String visualUrl) {
        this.visualUrl = visualUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCoverColor() {
        return coverColor;
    }

    public void setCoverColor(String coverColor) {
        this.coverColor = coverColor;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAnyImageUrl() {
        if (coverUrl != null) return coverUrl;
        if (visualUrl != null) return visualUrl;
        if (iconUrl != null) return iconUrl;
        //return "https://www.google.com/s2/favicons?domain=www.filmreporter.ro&alt=feed";
        return "http://www.liveandsocial.com/wp-content/uploads/2014/03/Feedly-logo.jpg";
    }

    public String getCategoriesAsText() {
        List<String> categoriesStrings = new ArrayList<>(this.categories.size());
        for (Category category : categories) {
            categoriesStrings.add(category.label);
        }
        String categoriesAsText = TextUtils.join(", ", categoriesStrings);
        return categoriesAsText;

    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", website='" + website + '\'' +
                ", categories=" + categories +
                ", updated=" + updated +
                ", subscribers=" + subscribers +
                ", velocity=" + velocity +
                ", iconUrl='" + iconUrl + '\'' +
                ", visualUrl='" + visualUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", coverColor='" + coverColor + '\'' +
                ", topics=" + topics +
                ", state='" + state + '\'' +
                '}';
    }
}
