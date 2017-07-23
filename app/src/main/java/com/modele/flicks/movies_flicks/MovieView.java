package com.modele.flicks.movies_flicks;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.modele.flicks.movies_flicks.models.Movie;

import com.squareup.picasso.Picasso;
import com.modele.flicks.movies_flicks.MovieView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import static com.modele.flicks.movies_flicks.R.layout.item_movie;
import static com.modele.flicks.movies_flicks.R.layout.item_popular_movie;

/**
 * Created by domin on 20-Jul-17.
 */
public class MovieView extends RelativeLayout {
    private ImageView ivPicture; private ImageView ivpopuImage;
    private TextView tvTitle;
    private TextView tvOverview;

    public MovieView(Context c) {
        this(c, null);
    }

    public MovieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

       // LayoutInflater.from(context).inflate(item_popular_movie, this, true);
        LayoutInflater.from(context).inflate(item_movie, this, true);
        setupChildren();
    }


    private void setupChildren() {

        ivPicture = ButterKnife.findById(this, R.id.ivMovieImage);
        tvTitle = ButterKnife.findById(this, R.id.tvTitle);
        tvOverview = ButterKnife.findById(this, R.id.tvOverview);
        //ivpopuImage = ButterKnife.findById(this, R.id.ivMovieImage1);
}


    public static MovieView inflate(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieView movieView = (MovieView) inflater.inflate(R.layout.movie_view, parent, false);
        return movieView;
    }


    private int getOrientation() {
        return getContext().getResources().getConfiguration().orientation;
    }


    private void drawImage(String path) {
        if (path != null && ivPicture != null) {
            Picasso.with(getContext()).load(path)
                    //.fit().centerCrop()
                    .transform(new RoundedCornersTransformation(20, 20))
                     .placeholder(R.mipmap.placeholder)
                    .into(ivPicture);
        }
    }


    //this is a TEST method to be deleted once the varying layouts config. is set up
    public void depending(Movie movie){
        String imageToLoad = null;

        if (movie.getRating() <= 5.0) {
            LayoutInflater.from(getContext()).inflate(item_movie, this, true);
            setupChildren();

            //determine l'orietation de l'ecran
            int orientation = getOrientation();
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imageToLoad = movie.getPosterPath();
                // imageToLoad = movie.getBackdropPath();
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
               // imageToLoad = movie.getBackdropPath();
                imageToLoad = movie.getBackdropPathLand();
            }

            drawImage(imageToLoad);

            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(truncateOverview(movie.getOverview()));

        } else {
            LayoutInflater.from(getContext()).inflate(R.layout.item_popular_movie, this, true);
            setupChildren();
            imageToLoad = movie.getBackdropPath();
            drawImage(imageToLoad);

        }
    }


    //display data retrieved from the movie
    public void setItem(Movie movie) {
        String imageToLoad = null;
        //depending(movie);
        if(movie.getRating()<=5.0)
        {
        //determine la quand l'orientation de l'ecran

        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageToLoad = movie.getPosterPath();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageToLoad = movie.getBackdropPathLand();
        }
        if (imageToLoad != null) {
            Picasso.with(getContext()).load(imageToLoad)
                    //.fit().centerCrop()
                    .transform(new RoundedCornersTransformation(20, 20))
                    .placeholder(R.mipmap.placeholder)
                    .into(ivPicture);
        }

        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(truncateOverview(movie.getOverview()));
    }else {

            LayoutInflater.from(getContext()).inflate(R.layout.item_popular_movie, this, true);
            setupChildren();
            imageToLoad = movie.getBackdropPath();
            drawImage(imageToLoad);
        }
    }

    //reduire la quantite de caractere
    private String truncateOverview(String overview) {
        if (overview.length() > 140) {

            overview = overview.substring(0, 137) + "...";
        }
        return overview;
    }
}
