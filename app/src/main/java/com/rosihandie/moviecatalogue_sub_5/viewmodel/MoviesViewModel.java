package com.rosihandie.moviecatalogue_sub_5.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rosihandie.moviecatalogue_sub_5.database.MovieHelper;
import com.rosihandie.moviecatalogue_sub_5.model.Movies;
import com.rosihandie.moviecatalogue_sub_5.model.TvShow;
import com.rosihandie.moviecatalogue_sub_5.utils.ApiClient;
import com.rosihandie.moviecatalogue_sub_5.utils.SearchMoviesInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rosihandie.moviecatalogue_sub_5.BuildConfig.API_KEY;

public class MoviesViewModel extends AndroidViewModel {
    private SearchMoviesInterface searchMoviesInterface;
    //private SearchTvInterface searchTvInterface;
    private final MutableLiveData<ArrayList<Movies>> listMovies = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movies>> listMoviesFavorite = new MutableLiveData<>();
    private final MovieHelper movieHelper;
    private final MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<TvShow>> listTvFavorite = new MutableLiveData<>();
    //private final TvHelper tvHelper;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        this.movieHelper = MovieHelper.getInstance(application);
        //this.tvHelper = TvHelper.getInstance(application);
    }

    public void setMovies() {
        searchMoviesInterface = ApiClient.getClient().create(SearchMoviesInterface.class);
        try {
            Call<String> authorized = searchMoviesInterface.getDiscoverMovie(API_KEY, "en-US");
            final ArrayList<Movies> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()){
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movies = list.getJSONObject(i);
                                Movies movieItems = new Movies(movies);
                                listItems.add(movieItems);
                            }

                            listMovies.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e){
            System.out.println("error" + e);
        }
    }

    public LiveData<ArrayList<Movies>> getMovies() {
        return listMovies;
    }

    public void setSearchmovies(String query) {
        searchMoviesInterface = ApiClient.getClient().create(SearchMoviesInterface.class);
        try {
            Call<String> authorized = searchMoviesInterface.getSearchMovie(API_KEY, "en-US", query);
            final ArrayList<Movies> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movies = list.getJSONObject(i);
                                Movies movieItems = new Movies(movies);
                                listItems.add(movieItems);
                            }
                            listMovies.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(getApplication(), "Something went wrong check your connection" + t, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public LiveData<ArrayList<Movies>> getSearchMovies() {
        return listMovies;
    }

    public void setTvShow() {
        searchMoviesInterface = ApiClient.getClient().create(SearchMoviesInterface.class);
        try {
            Call<String> authorized = searchMoviesInterface.getDiscoverTv(API_KEY, "en-US");
            final ArrayList<TvShow> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject tvShow = list.getJSONObject(i);
                                TvShow tvShowItems = new TvShow(tvShow);
                                listItems.add(tvShowItems);
                            }

                            listTvShow.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvShow;
    }

    public void setSearchTvShow(String query) {
        searchMoviesInterface = ApiClient.getClient().create(SearchMoviesInterface.class);
        try {
            Call<String> authorized = searchMoviesInterface.getSearchTelevision(API_KEY, "en-US", query);
            final ArrayList<TvShow> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject tvShow = list.getJSONObject(i);
                                TvShow tvShowItems = new TvShow(tvShow);
                                listItems.add(tvShowItems);
                            }

                            listTvShow.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }

    public LiveData<ArrayList<TvShow>> getSearchTvShow(){
        return listTvShow;
    }

    public void setMoviesDatabase(String type){
        ArrayList<Movies> movies = movieHelper.getAllMovies(type);
        listMoviesFavorite.postValue(movies);
    }

    public LiveData<ArrayList<Movies>> getMoviesFavorite(String type){
        setMoviesDatabase(type);
        return listMoviesFavorite;
    }

    public void setTvDatabase(String type){
        ArrayList<TvShow> tvShows = movieHelper.getAllTv(type);
        listTvFavorite.postValue(tvShows);
    }

    public LiveData<ArrayList<TvShow>> getTvFavorite(String type){
        setMoviesDatabase(type);
        return listTvFavorite;
    }

}
