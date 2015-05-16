package io.github.gdg_bucharest.gdg_feedly_client.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by pndl on 5/16/15.
 * http://blog.udinic.com/2013/04/24/write-your-own-android-authenticator/
 */
public class FeedlyAccountAuthenticator extends AbstractAccountAuthenticator {

    private Context context;

    public FeedlyAccountAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        return createIntent(response, accountType, authTokenType, true);
    }

    private Bundle createIntent(AccountAuthenticatorResponse response, String accountType, String authTokenType, boolean isAddingNewAccount) {
        final Intent intent = new Intent(context, FeedlyAuthenticatorActivity.class);
        intent.putExtra(FeedlyAuthenticatorActivity.ACCOUNT_TYPE, accountType);
        intent.putExtra(FeedlyAuthenticatorActivity.AUTH_TYPE, authTokenType);
        intent.putExtra(FeedlyAuthenticatorActivity.IS_ADDING_NEW_ACCOUNT, isAddingNewAccount);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        AccountManager accountManager = AccountManager.get(context);
        String authToken = accountManager.peekAuthToken(account, authTokenType);

        if (TextUtils.isEmpty(authToken)) {
            return createIntent(response, account.type, authTokenType, false);
        }

        Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        return result;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
