package io.github.gdg_bucharest.gdg_feedly_client.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pndl on 5/23/15.
 *
 * https://github.com/udacity/Sunshine/blob/6.10-update-map-intent/app/src/main/java/com/example/android/sunshine/app/data/WeatherContract.java
 * https://github.com/google/iosched/blob/4ef25b4981717bf17bd22061b05630875295719f/android/src/main/java/com/google/android/apps/iosched/provider/ScheduleContract.java
 */
public class FeedlyContract {

    public static final String CONTENT_AUTHORITY = "io.github.gdg_bucharest.gdg_feedly_client";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CATEGORY = "category";

    public static final class CategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;

        public static final String TABLE_NAME = "category";

        public static final String COLUMN_LABEL = "label";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
