package com.example.movieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;


    private String mQuery;
    private int mPageNumber;


    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getPop(){
        return movieApiClient.getMoviesPop();
    }
    public LiveData<List<MovieModel>> getTop(){
        return movieApiClient.getMoviesTop();
    }
    public LiveData<List<MovieModel>> getAction(){
        return movieApiClient.getMoviesAct();
    }
    public LiveData<List<MovieModel>> getTrending(){
        return movieApiClient.getTrend();
    }


    public void searchMovieApi(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApi(query, pageNumber);

    }

    public void searchMoviePop( int pageNumber) {

        mPageNumber = pageNumber;
        movieApiClient.searchMovieApiPop(pageNumber);
    }

    public void searchMovieTop(int pageNumber){
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApiTop(pageNumber);

    }

    public void searchMovieAct(int pageNumber){
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApiAct(pageNumber);
    }

    public void searchTrend(int pageNumber){
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApiTrend(pageNumber);
    }

    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber + 1);

    }

    public void searchNextPagePop(){
        searchMoviePop(mPageNumber + 1);

    }

    public void searchNextPageTop(){
        searchMovieTop(mPageNumber + 1);

    }

    public void searchNextPageAct(){
        searchMovieAct(mPageNumber + 1);

    }

    public void searchNextPageTrend(){
        searchTrend(mPageNumber + 1);

    }




}
