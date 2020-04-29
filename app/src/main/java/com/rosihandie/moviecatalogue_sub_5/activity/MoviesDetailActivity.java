package com.rosihandie.moviecatalogue_sub_5.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rosihandie.moviecatalogue_sub_5.R;
import com.rosihandie.moviecatalogue_sub_5.database.MovieHelper;
import com.rosihandie.moviecatalogue_sub_5.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MoviesDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIES = "extra_movies";
    private MovieHelper movieHelper;
    private Movies movies;
    private FloatingActionButton button;
    private Boolean isFavorite = false;
    private ProgressBar progressBar;

    public MoviesDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        final TextView titleMovies = findViewById(R.id.detail_name);
        final TextView descriptionMovies = findViewById(R.id.detail_description);
        final TextView releaseMovies = findViewById(R.id.detail_release);
        final TextView ratingMovies = findViewById(R.id.detail_rating);
        final ImageView photoMovies = findViewById(R.id.detail_photo);
        final ImageView backdropMovies = findViewById(R.id.detail_poster);

        button = findViewById(R.id.btn_fav);
        button.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar_detail);
        progressBar.setVisibility(View.VISIBLE);

        movies = getIntent().getParcelableExtra(EXTRA_MOVIES);
        final String moviesId = Integer.toString(movies.getId());

        //if (!isFinishing()) {

            String image_url = "http://image.tmdb.org/t/p/w185" + movies.getPhoto();
            String image_backdrop = "http://image.tmdb.org/t/p/w185" + movies.getBackdrop();

            titleMovies.setText(movies.getTitle());
            descriptionMovies.setText(movies.getDescription());
            releaseMovies.setText(movies.getRelease());
            ratingMovies.setText(movies.getRating());

            Picasso.get()
                    .load(image_url)
                    .placeholder(R.color.colorDefault)
                    .fit()
                    .into(photoMovies);

            Picasso.get()
                    .load(image_backdrop)
                    .placeholder(R.color.colorDefault)
                    .fit()
                    .into(backdropMovies);

            progressBar.setVisibility(View.GONE);
        //}

        Toolbar toolbar = findViewById(R.id.toolbar_detail_movies);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Detail Movies");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        if (movieHelper.checkMovies(moviesId)) {
            button.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
            isFavorite = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_fav)
            if (!isFavorite) {
                movies = getIntent().getParcelableExtra(EXTRA_MOVIES);
                movies.setType("movies");
                String favToast = getString(R.string.toastFav);
                String favFail = getString(R.string.toastFavFail);
                movieHelper.open();
                long result = movieHelper.insertMovies(movies);
                movieHelper.close();
                if (result > 0) {
                    button.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    Toast.makeText(getApplicationContext(), favToast, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), favFail, Toast.LENGTH_SHORT).show();
                }

            } else {
                movies.setType("movies");
                movieHelper.open();
                long result = movieHelper.deleteMovies(movies.getId());
                movieHelper.close();
                String favDel = getString(R.string.toastDel);

                if (result > 0) {
                    button.setBackground(getResources().getDrawable(R.drawable.ic_delete_black_24dp));
                    Toast.makeText(getApplicationContext(), favDel, Toast.LENGTH_SHORT).show();
                }
            }

    }

}
