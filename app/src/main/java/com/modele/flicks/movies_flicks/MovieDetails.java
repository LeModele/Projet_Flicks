package com.modele.flicks.movies_flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.modele.flicks.movies_flicks.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import static com.modele.flicks.movies_flicks.R.id.ivMovieImage;
public class MovieDetails extends AppCompatActivity {
    TextView tvTitle;
    TextView tvReleaseDate;
    RatingBar ratingBar;
    TextView tvSynopsis;
    ImageView ivImage;
    Button btnretour,btntrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        final Movie movie = (Movie) getIntent().getSerializableExtra("movie");

// find bouton
        btnretour =(Button)findViewById(R.id.btnretour);
        btntrailer=(Button)findViewById(R.id.btntrailer);
        //retrieve all fields and set their value
        tvTitle = ButterKnife.findById(this, R.id.title);
        tvTitle.setText(movie.getOriginalTitle());

        tvReleaseDate = ButterKnife.findById(this, R.id.release_date);
        tvReleaseDate.setText("Release date: " + movie.getReleaseDate());

        tvSynopsis = ButterKnife.findById(this, R.id.synopsis);
        tvSynopsis.setText(movie.getOverview());

        ratingBar = ButterKnife.findById(this, R.id.rating_bar);
        ratingBar.setRating((float) movie.getRating());

        ivImage = ButterKnife.findById(this, ivMovieImage);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                //launch video activity
                intent = new Intent(MovieDetails.this, MovieTrailer.class);

                if (intent != null) {
                    // put movie as "extra" into the bundle for access in YouTubeActivity
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }

            }
        });

        btnretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (MovieDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btntrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=null;
                intent=new Intent(MovieDetails.this,MovieTrailer.class);
                if(intent!=null){intent.putExtra("movie",movie); startActivity(intent);}
            }
        });

        Picasso.with(this).load(movie.getBackdropPath())
                .transform(new RoundedCornersTransformation(20, 20))
                .placeholder(R.mipmap.placeholder)
                .into(ivImage);

    }
}
