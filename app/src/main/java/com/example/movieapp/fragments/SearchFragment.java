package com.example.movieapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;


import com.example.movieapp.R;
import com.example.movieapp.adapters.MovieRecycleView;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.util.List;


public class SearchFragment extends Fragment implements OnMovieListener {


    private SearchView searchView;
    MovieListViewModel movieListViewModel;

    RecyclerView recyclerView_search;
    private MovieRecycleView movieRecycleViewAdapter_search;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);




        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SetupSearchView();

        recyclerView_search = view.findViewById(R.id.recyclerView_search);


        movieListViewModel = new MovieListViewModel();

        ConfigureRecyclerView();


        ObserveSearchMovies();

    }


    private void ObserveSearchMovies() {
        if (movieListViewModel.getMovies().hasObservers()) {
            movieListViewModel.getMovies().removeObservers((LifecycleOwner) getContext());
        }
        movieListViewModel.getMovies().observe((LifecycleOwner) getContext(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observe for any data change
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        // Get the data in log
                        Log.v("search", "onChanged: " + movieModel.getTitle());

                    }
                    movieRecycleViewAdapter_search.setmMovies(movieModels);
                }

            }
        } );
    }

    private void ConfigureRecyclerView(){

        movieRecycleViewAdapter_search = new MovieRecycleView((OnMovieListener) getContext());
        recyclerView_search.setAdapter(movieRecycleViewAdapter_search);
        recyclerView_search.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        recyclerView_search.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView_popular, int newState) {
                if (!recyclerView_popular.canScrollVertically(1)) {

                    movieListViewModel.searchNextPagePop();
                }
            }

        });




    }

    private void SetupSearchView() {
        SearchView searchView = getView().findViewById(R.id.searchView);


        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    movieListViewModel.searchMovieApi(query, 1);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
    }

    @Override
    public void onMovieClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}