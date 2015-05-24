package io.github.gdg_bucharest.gdg_feedly_client.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import io.github.gdg_bucharest.gdg_feedly_client.feedly.Category;

/**
 * Created by pndl on 5/23/15.
 */
public class FeedlyDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "feedly.db";
    private static final int DATABASE_VERSION = 1;

    private final Context context;

    public FeedlyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    interface Tables {
        String CATEGORY = "category";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.CATEGORY + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FeedlyContract.CategoryEntry.COLUMN_ID + " TEXT NOT NULL,"
                + FeedlyContract.CategoryEntry.COLUMN_ID + " TEXT NOT NULL,"
                + "UNIQUE (" + FeedlyContract.CategoryEntry.COLUMN_ID + ") ON CONFLICT REPLACE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
