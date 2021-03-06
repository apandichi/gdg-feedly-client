package io.github.gdg_bucharest.gdg_feedly_client.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by pndl on 5/23/15.
 */
public class FeedlyContentProvider extends ContentProvider {

    private static final int FEEDLY = 100; //TODO: may not be used at all. remove

    private static final int CATEGORY = 200;
    private static final int CATEGORY_ID = 201;


    private static final UriMatcher uriMatcher = buildUriMatcher();
    private FeedlyDatabase database;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FeedlyContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, FeedlyContract.PATH_CATEGORY, CATEGORY);
        matcher.addURI(authority, FeedlyContract.PATH_CATEGORY+ "/#", CATEGORY_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        database = new FeedlyDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = getCursor(uri, projection, selection, selectionArgs, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    private Cursor getCursor(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case CATEGORY:
                return database.getReadableDatabase().query(FeedlyContract.CategoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case CATEGORY_ID:
                return database.getReadableDatabase().query(FeedlyContract.CategoryEntry.TABLE_NAME, projection,
                        FeedlyContract.CategoryEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null, null, null, sortOrder);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case CATEGORY:
                return FeedlyContract.CategoryEntry.CONTENT_TYPE;
            case CATEGORY_ID:
                return FeedlyContract.CategoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri insertedUri = insertAndBuildUri(uri, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return insertedUri;
    }

    private Uri insertAndBuildUri(Uri uri, ContentValues values) {
        final SQLiteDatabase db = database.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            case CATEGORY:
                long id = db.insertOrThrow(FeedlyContract.CategoryEntry.TABLE_NAME, null, values);
                return FeedlyContract.CategoryEntry.buildLocationUri(id);
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
