package com.example.movieapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.utils.Credentials;

import java.util.List;

public class MovieRecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener mOnMovieListener;

    private static final int DISPLAY_MAIN = 1;

    private static final int DISPLAY_SEARCH = 2;

    public MovieRecycleView(OnMovieListener mOnMovieListener) {
        this.mOnMovieListener = mOnMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = null;

        if(viewType == DISPLAY_SEARCH) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        }else{
              view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_movie_layout, parent, false);

        }
        return new MovieViewHolder(view, mOnMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        int itemViewType = getItemViewType(position);
        if (itemViewType == DISPLAY_SEARCH) {

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + mMovies.get(position).getPoster_path())
                    .into(((MovieViewHolder) holder).imageView);

        }else {
            //Popular Movies
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + mMovies.get(position).getPoster_path())
                    .into(((MainViewHolder) holder).imageView22);

        }
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;

    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }


    public MovieModel getSelectedMovie(int position){
        if(mMovies != null){
            if(mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR){
            return DISPLAY_MAIN;
        }else{
            return DISPLAY_SEARCH;
        }
    }

}
