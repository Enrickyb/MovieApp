package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.models.MovieModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView imageViewDetails;
    private TextView titleDetails, descriptionDetails;
    private RatingBar ratingBarDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);



        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descriptionDetails = findViewById(R.id.textView_desc_details);
        ratingBarDetails = findViewById(R.id.ratingBar_details);
        ratingBarDetails.setNumStars(5);
        GetDataFromIntent();




    }

    private void GetDataFromIntent(){
        if(getIntent().hasExtra("movie")){
            MovieModel movie = getIntent().getParcelableExtra("movie");
            titleDetails.setText(movie.getTitle());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path())
                    .into(imageViewDetails);
            descriptionDetails.setText(movie.getMovie_overview());
            ratingBarDetails.setRating((movie.getVote_average())/2);
        }
    }

}