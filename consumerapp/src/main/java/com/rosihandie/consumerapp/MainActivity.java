package com.rosihandie.consumerapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rosihandie.consumerapp.adapter.FavMovieAdapter;
import com.rosihandie.consumerapp.database.MovieHelper;

import java.util.Objects;

import static com.rosihandie.consumerapp.database.DbContract.MovieColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMovie;
    FavMovieAdapter favMovieAdapter;
    private Cursor list_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovie = findViewById(R.id.rv_favorite_movies);
        MovieHelper movieHelper = MovieHelper.getInstance(this);
        movieHelper.open();
        new loadMovie().execute();
        showRecyclerMovie();
    }

    private void showRecyclerMovie() {
        favMovieAdapter = new FavMovieAdapter(this);
        rvMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvMovie.setAdapter(favMovieAdapter);
        rvMovie.setHasFixedSize(true);
        favMovieAdapter.setMovieList(list_movie);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("StaticFieldLeak")
    private class loadMovie extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getApplicationContext()).getContentResolver()
                    .query(
                            CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor_movie) {
            super.onPostExecute(cursor_movie);
            list_movie = cursor_movie;
            favMovieAdapter.setMovieList(list_movie);
            favMovieAdapter.notifyDataSetChanged();
        }
    }
}
