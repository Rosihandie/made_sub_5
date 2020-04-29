package com.rosihandie.moviecatalogue_sub_5.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rosihandie.moviecatalogue_sub_5.R;
import com.rosihandie.moviecatalogue_sub_5.adapter.FavMovieAdapter;
import com.rosihandie.moviecatalogue_sub_5.adapter.FavTvAdapter;
import com.rosihandie.moviecatalogue_sub_5.model.Movies;
import com.rosihandie.moviecatalogue_sub_5.model.TvShow;
import com.rosihandie.moviecatalogue_sub_5.viewmodel.MoviesViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private FavMovieAdapter favMovieAdapter;
    private FavTvAdapter favTvAdapter;
    private RecyclerView rvMovie, rvTv;
    private ProgressBar progressBar;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvMovie = view.findViewById(R.id.rv_favorite_movies);
        rvTv = view.findViewById(R.id.rv_favorite_tvshow);
        progressBar = view.findViewById(R.id.progress_load_favorite);

        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getMoviesFavorite("movies").observe(this, getMoviesFavorite);

        MoviesViewModel tvshowViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        tvshowViewModel.getTvFavorite("tvshow").observe(this, getTvFavorite);

        favMovieAdapter = new FavMovieAdapter(getActivity());
        favMovieAdapter.notifyDataSetChanged();

        favTvAdapter = new FavTvAdapter(getActivity());
        favTvAdapter.notifyDataSetChanged();

        moviesViewModel.setMoviesDatabase("movies");
        tvshowViewModel.setTvDatabase("tvshow");

        showRecyclerList();
        showLoading(true);

        rvMovie.setHasFixedSize(true);
        rvTv.setHasFixedSize(true);
        return view;
    }

    private void showRecyclerList(){
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMovie.setAdapter(favMovieAdapter);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTv.setAdapter(favTvAdapter);
    }

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    private final Observer<ArrayList<Movies>> getMoviesFavorite = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null) {
                favMovieAdapter.setMovieList(movies);
                showRecyclerList();
                showLoading(false);

            }else {

                showLoading(false);
            }
        }
    };

    private final Observer<ArrayList<TvShow>> getTvFavorite = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvshow) {
            if (tvshow != null) {
                favTvAdapter.setTvList(tvshow);
                showRecyclerList();
                showLoading(false);

            }else {

                showLoading(false);
            }
        }
    };

    public void onResume(){
        super.onResume();
        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.setMoviesDatabase("movies");

        MoviesViewModel tvshowViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        tvshowViewModel.setTvDatabase("tvshow");
    }

}
