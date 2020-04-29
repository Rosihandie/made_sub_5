package com.rosihandie.moviecatalogue_sub_5.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    public static final String TABLE_MOVIE = "movies";
    public static final String AUTHORITY = "com.rosihandie.moviecatalogue_sub_5";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        static final String MOVIES_ID = "id";
        static final String TVSHOW_ID = "id";
        public static final String TYPE = "type";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "overview";
        public static final String RATING = "vote_average";
        public static final String RELEASE_DATE = "release_date";
        public static final String URL_IMAGE = "poster_path";
        public static final String URL_BACKDROP = "backdrop_path";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

}
