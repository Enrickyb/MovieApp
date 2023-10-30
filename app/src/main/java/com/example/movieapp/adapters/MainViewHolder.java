package com.example.movieapp.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    ImageView imageView22;

    OnMovieListener onMovieListener;



    public MainViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        imageView22 = itemView.findViewById(R.id.movie_image);


        itemView.setOnClickListener(this);

    }

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {

        onMovieListener.onMovieClick(getBindingAdapterPosition());

    }
}
