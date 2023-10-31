package com.example.movieapp.utils;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );

    @GET("3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );

    @GET("3/movie/top_rated")
    Call<MovieSearchResponse> getTopRated(
            @Query("api_key") String key,
            @Query("page") int page
    );

    @GET("3/discover/movie")
    Call<MovieSearchResponse> getAction(
            @Query("api_key") String key,
            @Query("with_genres") int with_genres,
            @Query("page") int page
    );

    @GET("3/trending/all/day")
    Call<MovieSearchResponse> getTrending(
            @Query("api_key") String key,
            @Query("page") int page
    );


}
