package com.example.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.AppExecutors;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //search live data
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //popular live data
    private MutableLiveData<List<MovieModel>> mMoviesPop;
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;

    //top rated live data
    private MutableLiveData<List<MovieModel>> mMoviesTop;
    private RetrieveMoviesRunnableTop retrieveMoviesRunnableTop;

    //Actions films live data
    private MutableLiveData<List<MovieModel>> mMoviesAct;
    private RetrieveMoviesRunnableAct retrieveMoviesRunnableAct;

    //trending live data
    private MutableLiveData<List<MovieModel>> mTrend;
    private RetrieveTrendingRunnableTrend retrieveTrendingRunnableTrend;





    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
        mMoviesTop = new MutableLiveData<>();
        mMoviesAct = new MutableLiveData<>();
        mTrend = new MutableLiveData<>();
    }



    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop(){
        return mMoviesPop;
    }

    public LiveData<List<MovieModel>> getMoviesTop(){
        return mMoviesTop;
    }

    public LiveData<List<MovieModel>> getMoviesAct(){
        return mMoviesAct;
    }

    public LiveData<List<MovieModel>> getTrend(){
        return mTrend;
    }

    public void searchMovieApi(String query, int pageNumber){
        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);


        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }


    public void searchMovieApiPop(int pageNumber){
        if(retrieveMoviesRunnablePop != null){
            retrieveMoviesRunnablePop = null;
        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);


        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);


        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                myHandler2.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }


    public void searchMovieApiTop( int pageNumber){
        if(retrieveMoviesRunnableTop != null){
            retrieveMoviesRunnableTop = null;
        }

        retrieveMoviesRunnableTop = new RetrieveMoviesRunnableTop(pageNumber);
        final Future myHandler3 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnableTop);


        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                myHandler3.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }


    public void searchMovieApiAct(int pageNumber){
        if(retrieveMoviesRunnableAct != null){
            retrieveMoviesRunnableAct = null;
        }

        retrieveMoviesRunnableAct = new RetrieveMoviesRunnableAct(pageNumber);
        final Future myHandler4 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnableAct);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                myHandler4.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);

    }

    public void searchMovieApiTrend(int pageNumber){
        if(retrieveTrendingRunnableTrend != null){
            retrieveTrendingRunnableTrend = null;
        }

        retrieveTrendingRunnableTrend = new RetrieveTrendingRunnableTrend(pageNumber);
        final Future myHandler5 = AppExecutors.getInstance().networkIO().submit(retrieveTrendingRunnableTrend);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                myHandler5.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }


    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try{
                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        mMovies.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }


            if(cancelRequest){
                return;
            }
        }
            private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
                return Servicey.getMovieApi().searchMovie(
                        Credentials.API_KEY,
                        query,
                        pageNumber
                );
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }
    private class RetrieveMoviesRunnablePop implements Runnable{


        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int pageNumber) {

            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try{
                Response response2 = getPop(pageNumber).execute();
                if(cancelRequest){
                    return;
                }

                if(response2.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if(pageNumber == 1){
                        mMoviesPop.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }
                }
                else{
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesPop.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }


            if(cancelRequest){
                return;
            }
        }
        private Call<MovieSearchResponse> getPop(int pageNumber){
            return Servicey.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }
    private class RetrieveMoviesRunnableTop implements Runnable{


        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnableTop(int pageNumber) {

            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try{
                Response response3 = getTop(pageNumber).execute();
                if(cancelRequest){
                    return;
                }

                if(response3.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response3.body()).getMovies());
                    if(pageNumber == 1){
                        mMoviesTop.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mMoviesTop.getValue();
                        currentMovies.addAll(list);
                        mMoviesTop.postValue(currentMovies);
                    }
                }
                else{
                    String error = response3.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesTop.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesTop.postValue(null);
            }


            if(cancelRequest){
                return;
            }
        }
        private Call<MovieSearchResponse> getTop(int pageNumber){
            return Servicey.getMovieApi().getTopRated(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesRunnableAct implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnableAct(int pageNumber) {

            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try{
                Response response4 = getAct(pageNumber).execute();
                if(cancelRequest){
                    return;
                }

                if(response4.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response4.body()).getMovies());
                    if(pageNumber == 1){
                        mMoviesAct.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mMoviesAct.getValue();
                        currentMovies.addAll(list);
                        mMoviesAct.postValue(currentMovies);
                    }
                }
                else{
                    String error = response4.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesAct.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesAct.postValue(null);
            }


            if(cancelRequest){
                return;
            }
        }
        private Call<MovieSearchResponse> getAct(int pageNumber){
            return Servicey.getMovieApi().getAction(
                    Credentials.API_KEY,
                    28,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveTrendingRunnableTrend implements Runnable{


        private int pageNumber;
        boolean cancelRequest;

        public RetrieveTrendingRunnableTrend(int pageNumber) {

            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try{
                Response response5 = getTrend(pageNumber).execute();
                if(cancelRequest){
                    return;
                }

                if(response5.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response5.body()).getMovies());
                    if(pageNumber == 1){
                        mTrend.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mTrend.getValue();
                        currentMovies.addAll(list);
                        mTrend.postValue(currentMovies);
                    }
                }
                else{
                    String error = response5.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mTrend.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTrend.postValue(null);
            }


            if(cancelRequest){
                return;
            }
        }
        private Call<MovieSearchResponse> getTrend(int pageNumber){
            return Servicey.getMovieApi().getTrending(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }


}
