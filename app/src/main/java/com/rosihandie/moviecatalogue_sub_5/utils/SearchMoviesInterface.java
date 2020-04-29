package com.rosihandie.moviecatalogue_sub_5.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchMoviesInterface {

    @GET("discover/movie")
    Call<String> getReleaseMovie(@Query("api_key") String API_KEY,
                                 @Query("primary_release_date.gte") String ReleaseDate,
                                 @Query("primary_release_date.lte") String TodayDate);

    @GET("discover/movie")
    Call<String> getDiscoverMovie (@Query("api_key") String API_KEY,
                                   @Query("language") String language);

    @GET("search/movie")
    Call<String> getSearchMovie(@Query("api_key") String API_KEY,
                                @Query("language") String language,
                                @Query("query") String keyword);

    @GET("discover/tv")
    Call<String> getDiscoverTv(@Query("api_key") String API_KEY,
                               @Query("language") String language);

    @GET("search/tv")
    Call<String> getSearchTelevision(@Query("api_key") String API_KEY,
                                     @Query("language") String language,
                                     @Query("query") String keyword);


}
