package io.github.gdg_bucharest.gdg_feedly_client.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.gdg_bucharest.gdg_feedly_client.FeedlyRetrofitClient;
import io.github.gdg_bucharest.gdg_feedly_client.FeedlyService;
import io.github.gdg_bucharest.gdg_feedly_client.FeedlyServiceProvider;
import io.github.gdg_bucharest.gdg_feedly_client.auth.FeedlyAuthenticatorActivity;
import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;
import io.github.gdg_bucharest.gdg_feedly_client.provider.FeedlyContract;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.ContentProviderOperation.newInsert;

/**
 * Created by pndl on 5/16/15.
 * http://blog.udinic.com/2013/07/24/write-your-own-android-sync-adapter/
 */
public class FeedlySyncAdapter extends AbstractThreadedSyncAdapter {

    private final AccountManager accountManager;
    private Context context;

    public FeedlySyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        accountManager = AccountManager.get(context);
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, final ContentProviderClient provider, SyncResult syncResult) {
        try {
            String authToken = accountManager.blockingGetAuthToken(account, FeedlyAuthenticatorActivity.authTokenType, true);
            FeedlyService feedlyService = new FeedlyRetrofitClient(authToken).getFeedlyService();
            feedlyService.getCategories(new Callback<List<Category>>() {
                @Override
                public void success(List<Category> categories, Response response) {
                    final ArrayList<ContentProviderOperation> operations = Lists.newArrayList();
                    ContentProviderOperation.Builder builder = newInsert(FeedlyContract.CategoryEntry.CONTENT_URI);
                    for (Category category : categories) {
                        operations.add(
                                builder.withValue(FeedlyContract.CategoryEntry.COLUMN_ID, category.id)
                                        .withValue(FeedlyContract.CategoryEntry.COLUMN_ID, category.label)
                                        .build());
                    }
                    final ContentResolver resolver = context.getContentResolver();
                    try {
                        resolver.applyBatch(FeedlyContract.CONTENT_AUTHORITY, operations);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println(error.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
