package io.github.gdg_bucharest.gdg_feedly_client.feedly;

import java.util.List;

/**
 * Created by pndl on 3/19/15.
 */
public class StreamContents {

    private String id;
    private String continuation;
    private Long updated;
    private List<Entry> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContinuation() {
        return continuation;
    }

    public void setContinuation(String continuation) {
        this.continuation = continuation;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public List<Entry> getItems() {
        return items;
    }

    public void setItems(List<Entry> items) {
        this.items = items;
    }
}
