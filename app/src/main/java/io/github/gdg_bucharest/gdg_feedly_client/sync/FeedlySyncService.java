package io.github.gdg_bucharest.gdg_feedly_client.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by pndl on 5/16/15.
 */
public class FeedlySyncService extends Service {

    private FeedlySyncAdapter feedlySyncAdapter = new FeedlySyncAdapter(getApplicationContext(), true);

    @Override
    public IBinder onBind(Intent intent) {
        return feedlySyncAdapter.getSyncAdapterBinder();
    }
}
