package io.github.gdg_bucharest.gdg_feedly_client;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            // http://simpleprogrammer.com/2011/05/25/oauth-and-rest-in-android-part-1/
            // https://cwiki.apache.org/confluence/display/OLTU/OAuth+2.0+Client+Quickstart
            // https://groups.google.com/forum/#!topic/feedly-cloud/VoI4zk6yGEc
            // https://developer.feedly.com/v3/auth/
            OAuthClientRequest request = OAuthClientRequest
                    .authorizationLocation("https://sandbox.feedly.com/v3/auth/auth")
                    .setClientId("sandbox")
                    .setRedirectURI("http://localhost:8080")
                    .setParameter("scope", "https://cloud.feedly.com/subscriptions")
                    .setParameter("response_type", "code")
                    .buildQueryMessage();

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(request.getLocationUri()));
            startActivity(intent);


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
