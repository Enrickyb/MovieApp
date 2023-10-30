package com.example.movieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    ImageView imageView;

    OnMovieListener onMovieListener;



    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        imageView = itemView.findViewById(R.id.movie_image);


        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        onMovieListener.onMovieClick(getBindingAdapterPosition());

    }
}
