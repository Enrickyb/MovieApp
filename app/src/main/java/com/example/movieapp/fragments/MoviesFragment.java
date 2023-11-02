package com.example.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MovieDetailsActivity;
import com.example.movieapp.R;
import com.example.movieapp.adapters.MovieRecycleView;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.util.List;
import java.util.Random;

public class MoviesFragment extends Fragment implements OnMovieListener {


    private MovieListViewModel movieListViewModel;


    private RecyclerView recyclerView_popular;

    private MovieRecycleView movieRecycleViewAdapter_popular;


    private RecyclerView recyclerView_topRated;

    private MovieRecycleView movieRecycleViewAdapter_topRated;


    private RecyclerView recyclerView_action;

    private MovieRecycleView movieRecycleViewAdapter_action;


    private RecyclerView recyclerView_trend;

    private MovieRecycleView movieRecycleViewAdapter_trend;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movies, container, false);


        recyclerView_trend = view.findViewById(R.id.recyclerView_trend);
        recyclerView_popular = view.findViewById(R.id.recyclerView_popular);
        recyclerView_topRated = view.findViewById(R.id.recyclerView_topRated);
        recyclerView_action = view.findViewById(R.id.recyclerView_action);



        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);




        ConfigureRecyclerView();
        ObserveActionTrend();
        ObservePopularMovies();
        ObserveTopRatedMovies();
        ObserveActionMovies();

        movieListViewModel.searchMovieApiTrend(1);
        movieListViewModel.searchMovieApiPop(1);
        movieListViewModel.searchMovieApiTop(1);
        movieListViewModel.searchMovieApiAction(1);

        return view;
    }


    private void ObserveActionTrend() {
        movieListViewModel.getTrend().observe((LifecycleOwner) getContext(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observe for any data change
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        // Get the data in log
                        Log.v("trend", "onChanged: " + movieModel.getTitle());
                    }

                    Random rand = new Random();
                    int pageRandom = rand.nextInt(movieModels.size());




                    MovieModel randomMovie = movieModels.get(pageRandom);
                    while (randomMovie.getBackdrop_path() == null && randomMovie.getBackdrop_path().isEmpty() && randomMovie.getTitle() == null && randomMovie.getTitle().isEmpty()){
                        pageRandom = rand.nextInt(movieModels.size());
                        randomMovie = movieModels.get(pageRandom);
                    }


                    Log.v("randomized movie", "onChanged: " + randomMovie.getTitle());


                    movieRecycleViewAdapter_trend.setmMovies(movieModels);
                }

            }
        } );

    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe((LifecycleOwner) getContext(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observe for any data change
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        // Get the data in log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                    }
                    movieRecycleViewAdapter_popular.setmMovies(movieModels);
                }
            }
        } );

    }
    private void ObserveTopRatedMovies() {
        movieListViewModel.getTop().observe((LifecycleOwner) getContext(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observe for any data change
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        // Get the data in log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                    }
                    movieRecycleViewAdapter_topRated.setmMovies(movieModels);
                }
            }
        } );
    }

    private void ObserveActionMovies() {
        movieListViewModel.getAction().observe((LifecycleOwner) getContext(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observe for any data change
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        // Get the data in log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                    }
                    movieRecycleViewAdapter_action.setmMovies(movieModels);
                }
            }
        } );

    }

    private void ConfigureRecyclerView(){

        movieRecycleViewAdapter_popular = new MovieRecycleView((OnMovieListener) getContext());
        recyclerView_popular.setAdapter(movieRecycleViewAdapter_popular);
        recyclerView_popular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        movieRecycleViewAdapter_topRated = new MovieRecycleView(this);
        recyclerView_topRated.setAdapter(movieRecycleViewAdapter_topRated);
        recyclerView_topRated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        movieRecycleViewAdapter_action = new MovieRecycleView(this);
        recyclerView_action.setAdapter(movieRecycleViewAdapter_action);
        recyclerView_action.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        movieRecycleViewAdapter_trend = new MovieRecycleView(this);
        recyclerView_trend.setAdapter(movieRecycleViewAdapter_trend);
        recyclerView_trend.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));



        recyclerView_popular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView_popular, int newState) {
                if (!recyclerView_popular.canScrollVertically(1)) {

                    movieListViewModel.searchNextPagePop();
                }
            }

        });


        recyclerView_topRated.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView_topRated, int newState) {
                if (!recyclerView_topRated.canScrollVertically(1)) {

                    movieListViewModel.searchNextPageTop();
                }
            }

        });

        recyclerView_action.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView_action, int newState) {
                if (!recyclerView_action.canScrollVertically(1)) {

                    movieListViewModel.searchNextPageAct();
                }
            }

        });

        recyclerView_trend.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView_action, int newState) {
                if (!recyclerView_trend.canScrollVertically(1)) {

                    movieListViewModel.searchNextPageAct();
                }
            }

        });


    }

    @Override
    public void onMovieClick(int position) {

        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        MovieModel selectedMovie = null;

        //TODO: Fix this
        if(selectedMovie != null){
            intent.putExtra("movie", selectedMovie);
            startActivity(intent);
        }
    }

    @Override
    public void onCategoryClick(String category) {

    }
}