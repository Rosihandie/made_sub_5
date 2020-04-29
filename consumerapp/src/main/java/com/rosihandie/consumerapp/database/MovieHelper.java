package com.rosihandie.consumerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.rosihandie.consumerapp.database.DbContract.MovieColumns.MOVIES_ID;
import static com.rosihandie.consumerapp.database.DbContract.TABLE_MOVIE;

public class MovieHelper {

    private static DbHelper databaseHelper;
    private static SQLiteDatabase database;
    private static MovieHelper INSTANCE;
    private static final String DATABASE_TABLE = TABLE_MOVIE;

    private MovieHelper(Context context) {
        databaseHelper = new DbHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public int deleteMovies(int id) {
        return database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }

    public int deleteTv(int id) {
        return database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }

    public boolean checkMovies(String id) throws SQLException{
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * from " + DATABASE_TABLE
                + " where " + MOVIES_ID + "=?", new String[]{id});

        boolean checkMovies = false;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                checkMovies = true;
            } while (cursor.moveToNext());

        }
            assert cursor != null;
            cursor.close();
            return checkMovies;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

}
