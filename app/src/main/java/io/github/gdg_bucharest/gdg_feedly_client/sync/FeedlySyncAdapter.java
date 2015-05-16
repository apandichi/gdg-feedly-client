package io.github.gdg_bucharest.gdg_feedly_client.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by pndl on 5/16/15.
 * http://blog.udinic.com/2013/07/24/write-your-own-android-sync-adapter/
 */
public class FeedlySyncAdapter extends AbstractThreadedSyncAdapter {

    public FeedlySyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
    }
}
