package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import io.github.gdg_bucharest.gdg_feedly_client.slidingmenu.AttachExample;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        WebView webview = new WebView(this) {};
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setVisibility(View.VISIBLE);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("http://localhost")) {
                    Uri uri = Uri.parse(url);
                    String code = uri.getQueryParameter("code");
                    System.out.println(code);
                    requestAccessToken(code);
                    return true;
                }
                return false;
            }
        });
        setContentView(webview);

//        setContentView(R.layout.activity_main);


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
        }
    }

    private void requestAccessToken(String code) {
        try {
            final OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation("https://sandbox.feedly.com/v3/auth/token")
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId("sandbox")
                    .setClientSecret("8LDQOW8KPYFPCQV2UL6J")
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
                    FeedlyServiceProvider feedlyServiceProvider = new FeedlyServiceProvider(MainActivity.this);
                    feedlyServiceProvider.setAccessToken(oAuthAccessTokenResponse.getAccessToken());
                    feedlyServiceProvider.setRefreshToken(oAuthAccessTokenResponse.getRefreshToken());

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }.execute();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
