package io.github.gdg_bucharest.gdg_feedly_client.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by pndl on 5/17/15.
 */
public class FeedlyAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new FeedlyAccountAuthenticator(this).getIBinder();
    }
}
