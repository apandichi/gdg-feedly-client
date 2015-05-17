package io.github.gdg_bucharest.gdg_feedly_client.auth;

/**
 * Created by pndl on 5/17/15.
 */
public interface FeedlyAccessTokenReceiver {
    public void receive(String accessToken);
}
