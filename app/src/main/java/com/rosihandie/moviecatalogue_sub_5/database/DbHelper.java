package com.rosihandie.moviecatalogue_sub_5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //static variable
    private static final int DATABASE_VERSION = 1;

    //database name
    private static final String DATABASE_NAME = "moviecatalogue";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",

            DbContract.TABLE_MOVIE,
            DbContract.MovieColumns._ID,
            DbContract.MovieColumns.MOVIES_ID,
            DbContract.MovieColumns.TYPE,
            DbContract.MovieColumns.TITLE,
            DbContract.MovieColumns.DESCRIPTION,
            DbContract.MovieColumns.RATING,
            DbContract.MovieColumns.RELEASE_DATE,
            DbContract.MovieColumns.URL_IMAGE,
            DbContract.MovieColumns.URL_BACKDROP
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_MOVIE);
        onCreate(db);
    }
}
