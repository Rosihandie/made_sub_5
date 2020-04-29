package com.rosihandie.moviecatalogue_sub_5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rosihandie.moviecatalogue_sub_5.model.Movies;
import com.rosihandie.moviecatalogue_sub_5.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.DESCRIPTION;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.MOVIES_ID;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.RATING;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.RELEASE_DATE;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.TYPE;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.TABLE_MOVIE;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.TITLE;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.TVSHOW_ID;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.URL_BACKDROP;
import static com.rosihandie.moviecatalogue_sub_5.database.DbContract.MovieColumns.URL_IMAGE;

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

    public ArrayList<Movies> getAllMovies(String type) {
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE,
                new String[]{_ID, TITLE, TYPE, DESCRIPTION, RATING, RELEASE_DATE, URL_IMAGE, URL_BACKDROP},
                TYPE + " = ?",
                new String[]{type},
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ArrayList<Movies> arrayList = new ArrayList<>();
        Movies movies;
        if (cursor.getCount() > 0) {
            do {
                movies = new Movies();
                movies.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movies.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                movies.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movies.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movies.setRating(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                movies.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movies.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(URL_IMAGE)));
                movies.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(URL_BACKDROP)));

                arrayList.add(movies);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovies(Movies movies) {
        ContentValues args = new ContentValues();
        args.put(_ID, movies.getId());
        args.put(MOVIES_ID, movies.getId());
        args.put(TYPE, movies.getType());
        args.put(TITLE, movies.getTitle());
        args.put(DESCRIPTION, movies.getDescription());
        args.put(RATING, movies.getRating());
        args.put(RELEASE_DATE, movies.getRelease());
        args.put(URL_IMAGE, movies.getPhoto());
        args.put(URL_BACKDROP, movies.getBackdrop());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public ArrayList<TvShow> getAllTv(String type) {
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE,
                new String[]{_ID, TITLE, TYPE, DESCRIPTION, RELEASE_DATE, RATING, URL_IMAGE, URL_BACKDROP},
                TYPE + " = ?",
                new String[]{type},
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ArrayList<TvShow> arrayList = new ArrayList<>();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tvShow.setRating(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                tvShow.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                tvShow.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(URL_IMAGE)));
                tvShow.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(URL_BACKDROP)));

                arrayList.add(tvShow);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvShow.getId());
        args.put(TVSHOW_ID, tvShow.getId());
        args.put(TYPE, tvShow.getType());
        args.put(TITLE, tvShow.getTitle());
        args.put(DESCRIPTION, tvShow.getDescription());
        args.put(RATING, tvShow.getRating());
        args.put(RELEASE_DATE, tvShow.getRelease());
        args.put(URL_IMAGE, tvShow.getPhoto());
        args.put(URL_BACKDROP, tvShow.getBackdrop());

        return database.insert(DATABASE_TABLE, null, args);
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
