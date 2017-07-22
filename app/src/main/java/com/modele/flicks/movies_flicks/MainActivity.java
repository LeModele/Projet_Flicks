package com.modele.flicks.movies_flicks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.modele.flicks.movies_flicks.adapter.MoviesAdapter;
import com.modele.flicks.movies_flicks.models.Movie;
import com.modele.flicks.movies_flicks.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private MoviesAdapter movieAdapter;
    private ListView lvMovies;
    private SwipeRefreshLayout swipeContainer;

    //private final AsyncHttpClient client = new AsyncHttpClient();

    private final String URL = Utils.getBaseURL();//+"now_playing?api_key="+Utils.getMovieDBAPIkey();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // setTitle("les Films");
       // getActionBar().setIcon(R.drawable.movie);
        //set up adapter

        lvMovies = ButterKnife.findById(this, R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MoviesAdapter(this, movies);
        lvMovies.setAdapter(movieAdapter);

        // setup refresh listener which triggers new data loading
        swipeContainer = ButterKnife.findById(this, R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //CLEAR OUT old items before appending in the new ones:
                movieAdapter.clear();
                movies.clear();
                movieAdapter.notifyDataSetChanged();

                fetchData(Utils.getClient());
                //fetchHardcodedData();

                //signal refresh has finished:
                swipeContainer.setRefreshing(false);
            }
        });


        //fetchHardcodedData();
        fetchData(Utils.getClient());

        setUpClickListener();

    }




    private void setUpClickListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view is an instance of MovieView
                //Expose details of movie (ratings (out of 10), popularity, and synopsis
                //ratings using RatingBar
                Movie movie = movies.get(position);

                Intent intent = null;

                if (movie.getRating() > 5.0) {
                    //launch video activity
                    intent = new Intent(MainActivity.this, MovieTrailer.class);
                    //intent = new Intent(MainActivity.this, MovieDetails.class);
                } else {
                    intent = new Intent(MainActivity.this, MovieDetails.class);
                }

                if(intent != null){
                    // put movie as "extra" into the bundle for access in the second activity
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }
            }
        });
    }


    private void fetchData(AsyncHttpClient client) {

        //make sure there's access to the web
        boolean connectivity = Utils.checkForConnectivity(this);

        if (!connectivity) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
// Add the buttons
            builder.setTitle("Connexion");
            builder.setMessage("Verifier la Connexion internet");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    fetchData(Utils.getClient());
                }
            });

// Set other dialog properties


// Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
            // Showing Alert Message

            Toast.makeText(this, "Impossible de continuer, verifier la connxion internet", Toast.LENGTH_LONG).show();
        }

        else {
            client.get(URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray movieJsonResults = null;

                    try {
                        movieJsonResults = response.getJSONArray("results");
                        movies.addAll(Movie.fromJSONArray(movieJsonResults));
                        movieAdapter.notifyDataSetChanged();

                        //signal refresh has finished:
                        swipeContainer.setRefreshing(false);

                        Log.d("DEBUG", movies.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }
}
