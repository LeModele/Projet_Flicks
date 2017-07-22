package com.modele.flicks.movies_flicks.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.modele.flicks.movies_flicks.models.Movie;
import java.util.ArrayList;
import java.util.List;
import com.modele.flicks.movies_flicks.MovieView;
import android.widget.ArrayAdapter;
/**
 * Created by domin on 20-Jul-17.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //use of subclassing to display every movie as part of the listView
        Movie movie = getItem(position);
        MovieView movieView = (MovieView)convertView;
        if(movieView == null){
            movieView = MovieView.inflate(parent);
        }
        movieView.setItem(movie);
        return movieView;
    }
}
