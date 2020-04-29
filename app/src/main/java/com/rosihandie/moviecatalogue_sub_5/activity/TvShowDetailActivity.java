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
import com.rosihandie.moviecatalogue_sub_5.model.TvShow;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private MovieHelper tvHelper;
    private TvShow tvShow;
    private FloatingActionButton button;
    private Boolean isFavorite = false;
    private ProgressBar progressBar;

    public TvShowDetailActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        TextView titleTvshow = findViewById(R.id.detail_name_tv);
        TextView descriptionTvshow = findViewById(R.id.detail_description_tv);
        TextView releaseTvshow = findViewById(R.id.detail_release_tv);
        TextView ratingTvshow = findViewById(R.id.detail_rating_tv);
        ImageView photoTvshow = findViewById(R.id.detail_photo_tv);
        ImageView backdropTvshow = findViewById(R.id.detail_poster_tv);

        button = findViewById(R.id.btn_fav_tv);
        button.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar_detail_tv);
        progressBar.setVisibility(View.VISIBLE);

        tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        final String tvId = Integer.toString(tvShow.getId());

        if (!isFinishing()) {

            String image_url = "https://image.tmdb.org/t/p/w185" + tvShow.getPhoto();
            String image_backdrop = "https://image.tmdb.org/t/p/w185" + tvShow.getBackdrop();

            titleTvshow.setText(tvShow.getTitle());
            descriptionTvshow.setText(tvShow.getDescription());
            releaseTvshow.setText(tvShow.getRelease());
            ratingTvshow.setText(tvShow.getRating());

            Picasso.get()
                    .load(image_url)
                    .placeholder(R.color.colorDefault)
                    .fit()
                    .into(photoTvshow);

            Picasso.get()
                    .load(image_backdrop)
                    .placeholder(R.color.colorDefault)
                    .fit()
                    .into(backdropTvshow);

            progressBar.setVisibility(View.GONE);
        }

        Toolbar toolbar = findViewById(R.id.toolbar_detail_tv);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Detail Tv Show");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        tvHelper = MovieHelper.getInstance(getApplicationContext());
        if (tvHelper.checkMovies(tvId)) {
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
        if (v.getId() == R.id.btn_fav_tv)
            if (!isFavorite) {
                tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
                tvShow.setType("tvshow");
                String favToast = getString(R.string.toastFav);
                String favFail = getString(R.string.toastFavFail);
                tvHelper.open();
                long result = tvHelper.insertTv(tvShow);
                tvHelper.close();
                if (result > 0) {
                    button.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    Toast.makeText(getApplicationContext(), favToast, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), favFail, Toast.LENGTH_SHORT).show();
                }

            } else {
                tvShow.setType("tvshow");
                tvHelper.open();
                long result = tvHelper.deleteTv(tvShow.getId());
                tvHelper.close();
                String favDel = getString(R.string.toastDel);

                if (result > 0) {
                    button.setBackground(getResources().getDrawable(R.drawable.ic_delete_black_24dp));
                    Toast.makeText(getApplicationContext(), favDel, Toast.LENGTH_SHORT).show();
                }
            }

    }
}
