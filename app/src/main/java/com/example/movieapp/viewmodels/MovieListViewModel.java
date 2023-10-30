package com.example.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {


   private MovieRepository movieRepository;

    public MovieListViewModel(){
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getPop(){
        return movieRepository.getPop();
    }
    public LiveData<List<MovieModel>> getTop(){
        return movieRepository.getTop();
    }

    public LiveData<List<MovieModel>> getAction(){
        return movieRepository.getAction();
    }

    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query, pageNumber);
    }
    public void searchMovieApiPop(int pageNumber){
        movieRepository.searchMoviePop(pageNumber);
    }
    public void searchMovieApiTop(int pageNumber){
        movieRepository.searchMovieTop(pageNumber);
    }

    public void searchMovieApiAction(int pageNumber){
        movieRepository.searchMovieAct(pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }

    public void searchNextPagePop(){
        movieRepository.searchNextPagePop();
    }

    public void searchNextPageTop(){
        movieRepository.searchNextPageTop();
    }

    public void searchNextPageAct(){
        movieRepository.searchNextPageAct();
    }





}
