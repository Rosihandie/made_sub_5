package com.rosihandie.moviecatalogue_sub_5.Fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rosihandie.moviecatalogue_sub_5.R;
import com.rosihandie.moviecatalogue_sub_5.adapter.MoviesAdapter;
import com.rosihandie.moviecatalogue_sub_5.model.Movies;
import com.rosihandie.moviecatalogue_sub_5.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    View view;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private MoviesViewModel moviesViewModel;
    private RecyclerView rvMovies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_movies, container, false);
        rvMovies = view.findViewById(R.id.rv_movies);

        progressBar = view.findViewById(R.id.progress_load);

        moviesViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MoviesViewModel.class);
        moviesViewModel.getMovies().observe(this, getMovies);
        moviesViewModel.setMovies();

        moviesAdapter = new MoviesAdapter(getActivity());
        moviesAdapter.notifyDataSetChanged();

        showLoading(true);

        showRecyclerMovies();

        rvMovies.setHasFixedSize(true);
        setHasOptionsMenu(true);

        return view;
    }

    private void showRecyclerMovies() {
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(moviesAdapter);
    }

    private final Observer<ArrayList<Movies>> getMovies = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null) {
                moviesAdapter.setListMovies(movies);
                showRecyclerMovies();
                showLoading(false);

            } else {

                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.settings, menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    moviesViewModel.getSearchMovies().observe(Objects.requireNonNull(getActivity()), getSearchMovies);
                    moviesViewModel.setSearchmovies(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                moviesViewModel.setMovies();
                return true;
            }
        });
    }

    private final Observer<ArrayList<Movies>> getSearchMovies = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null) {
                moviesAdapter.setListMovies(movies);
                showRecyclerMovies();
                showLoading(false);
                if (movies.size() == 0)
                    Toast.makeText(getContext(), "No Movies", Toast.LENGTH_SHORT).show();

            } else {

                showLoading(false);
            }
        }
    };
}
