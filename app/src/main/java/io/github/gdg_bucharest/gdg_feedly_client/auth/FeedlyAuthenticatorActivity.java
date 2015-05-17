package io.github.gdg_bucharest.gdg_feedly_client.auth;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import io.github.gdg_bucharest.gdg_feedly_client.HomeActivity;

/**
 * Created by pndl on 5/16/15.
 */
public class FeedlyAuthenticatorActivity extends AccountAuthenticatorActivity {

    private static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    private static final String IS_ADDING_NEW_ACCOUNT = "IS_ADDING_NEW_ACCOUNT";

    private static final String accountName = "GDG Feedly";
    // must be identical to the account typ defined in authenticator.xml
    private static final String accountType = "io.github.gdg_bucharest.gdg_feedly_client.auth";

    public static final String authTokenType = "feedly";
    public static final Account account = new Account(accountName, accountType);


    public static Bundle newIntent(Context context, AccountAuthenticatorResponse response, String accountType, String authTokenType, boolean isAddingNewAccount) {
        final Intent intent = new Intent(context, FeedlyAuthenticatorActivity.class);
        intent.putExtra(FeedlyAuthenticatorActivity.ACCOUNT_TYPE, accountType);
        intent.putExtra(FeedlyAuthenticatorActivity.IS_ADDING_NEW_ACCOUNT, isAddingNewAccount);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String authToken = getIntent().getStringExtra(AccountManager.KEY_AUTHTOKEN);
        final String accountType = getIntent().getStringExtra(ACCOUNT_TYPE);
        //final String authTokenType = getIntent().getStringExtra(AUTH_TOKEN_TYPE);
        final boolean isAddingNewaccount = getIntent().getBooleanExtra(IS_ADDING_NEW_ACCOUNT, false);

        FeedlyWebviewAuthentication webviewAuthentication = new FeedlyWebviewAuthentication(this, new FeedlyAccessTokenReceiver() {
            @Override
            public void receive(String accessToken) {
                AccountManager accountManager = AccountManager.get(FeedlyAuthenticatorActivity.this);
                if (isAddingNewaccount) {
                    accountManager.addAccountExplicitly(account, null, null);
                    accountManager.setAuthToken(account, authTokenType, authToken);
                }
                setAccountAuthenticatorResult(getIntent().getExtras());
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
        setContentView(webviewAuthentication.createWebView());
        webviewAuthentication.createOAuthRequest();
    }
}
