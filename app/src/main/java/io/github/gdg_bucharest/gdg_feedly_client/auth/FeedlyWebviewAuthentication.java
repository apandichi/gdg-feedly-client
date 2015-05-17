package io.github.gdg_bucharest.gdg_feedly_client.auth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import io.github.gdg_bucharest.gdg_feedly_client.HomeActivity;

/**
 * Created by pndl on 5/17/15.
 */
public class FeedlyWebviewAuthentication {

    private Context context;
    private FeedlyAccessTokenReceiver feedlyAccessTokenReceiver;

    private WebView webview;

    public FeedlyWebviewAuthentication(Context context, FeedlyAccessTokenReceiver feedlyAccessTokenReceiver) {
        this.context = context;
        this.feedlyAccessTokenReceiver = feedlyAccessTokenReceiver;
    }

    public void createOAuthRequest() {
        try {
            // http://simpleprogrammer.com/2011/05/25/oauth-and-rest-in-android-part-1/
            // https://cwiki.apache.org/confluence/display/OLTU/OAuth+2.0+Client+Quickstart
            // https://groups.google.com/forum/#!topic/feedly-cloud/VoI4zk6yGEc
            // https://developer.feedly.com/v3/auth/
            final OAuthClientRequest request = OAuthClientRequest
                    .authorizationLocation("https://sandbox.feedly.com/v3/auth/auth")
                    .setClientId("sandbox")
                    .setRedirectURI("http://localhost:8080")
                    .setParameter("scope", "https://cloud.feedly.com/subscriptions")
                    .setParameter("response_type", "code")
                    .buildQueryMessage();
            webview.loadUrl(request.getLocationUri());
        } catch (OAuthSystemException e) {
            e.printStackTrace();
            // show a retry button
        }
    }

    public WebView createWebView() {
        webview = new WebView(context) {};
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setVisibility(View.VISIBLE);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null || !url.startsWith("http://localhost")) {
                    return false;
                }
                Uri uri = Uri.parse(url);
                String code = uri.getQueryParameter("code");
                System.out.println(code);
                requestAccessToken(code);
                return true;
            }
        });
        return webview;
    }

    private void requestAccessToken(String code) {
        try {
            final OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation("https://sandbox.feedly.com/v3/auth/token")
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId("sandbox")
                    .setClientSecret("4205DQXBAP99S8SUHXI3")
                    .setRedirectURI("http://localhost")
                    .setCode(code)
                    .buildQueryMessage();
            new AsyncTask<Void, Void, OAuthAccessTokenResponse>() {
                @Override
                protected OAuthAccessTokenResponse doInBackground(Void... params) {
                    OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
                    try {
                        OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request,
                                OAuthJSONAccessTokenResponse.class);
                        return oAuthResponse;
                    } catch (OAuthProblemException | OAuthSystemException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                protected void onPostExecute(OAuthAccessTokenResponse oAuthAccessTokenResponse) {
                    String accessToken = oAuthAccessTokenResponse.getAccessToken();
                    feedlyAccessTokenReceiver.receive(accessToken);
                }
            }.execute();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
    }

}
