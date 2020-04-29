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
import com.rosihandie.moviecatalogue_sub_5.adapter.TvShowAdapter;
import com.rosihandie.moviecatalogue_sub_5.model.TvShow;
import com.rosihandie.moviecatalogue_sub_5.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    View view;
    private TvShowAdapter tvShowAdapter;
    private ProgressBar progressBar;
    private MoviesViewModel tvshowViewModel;
    private RecyclerView rvTvshow;
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>();

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        rvTvshow = view.findViewById(R.id.rv_tv_show);

        progressBar = view.findViewById(R.id.progress_load_tv);

        tvshowViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MoviesViewModel.class);
        tvshowViewModel.getTvShow().observe(this, getTvShow);
        tvshowViewModel.setTvShow();

        tvShowAdapter = new TvShowAdapter(getActivity());
        tvShowAdapter.notifyDataSetChanged();

        showLoading(true);

        showRecyclerMovies();

        rvTvshow.setHasFixedSize(true);
        setHasOptionsMenu(true);

        return view;
    }

    private void showRecyclerMovies() {
        rvTvshow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvshow.setAdapter(tvShowAdapter);
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShow) {
            if (tvShow != null) {
                tvShowAdapter.setListTvshow(tvShow);
                showRecyclerMovies();
                showLoading(false);
            }else {

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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.settings, menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null){
            SearchView searchView = (SearchView)(menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    tvshowViewModel.getSearchTvShow().observe(Objects.requireNonNull(getActivity()), getSearchTvShow);
                    tvshowViewModel.setSearchTvShow(query);
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
                tvshowViewModel.setTvShow();
                return true;
            }
        });
    }

    private final Observer<ArrayList<TvShow>> getSearchTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                tvShowAdapter.setListTvshow(tvShows);
                showRecyclerMovies();
                showLoading(false);
                if (tvShows.size() == 0)
                    Toast.makeText(getContext(), "No Movies", Toast.LENGTH_SHORT).show();

            }else {

                showLoading(false);
            }
        }
    };
}
