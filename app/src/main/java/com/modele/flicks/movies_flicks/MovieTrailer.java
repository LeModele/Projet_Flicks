package com.modele.flicks.movies_flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.modele.flicks.movies_flicks.Utils.Utils;
import com.modele.flicks.movies_flicks.models.Movie;
import com.modele.flicks.movies_flicks.models.Trailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieTrailer extends YouTubeBaseActivity  {

    private ArrayList<Trailer> trailers;
    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        //retrieve movie that's been 'sent' from main activity
       movie = (Movie) getIntent().getSerializableExtra("movie");

        //retrieve ID of movie
        int movieID = movie.getID();
        String lien="/videos?api_key=";
        trailers = new ArrayList<>();

       // String url = Utils.getBaseURL() + Integer.toString(movieID) + "209112/videos?api_key=" + Utils.getMovieDBAPIkey();
        String url = Utils.getBaseURLtube() + Integer.toString(movieID) + lien + Utils.getMovieDBAPIkey();
        fetchMovieVideos(url);

    }

        private void fetchMovieVideos(String url) {
        //verifier la connexion internet
        boolean connectivity = Utils.checkForConnectivity(this);

        if (!connectivity) {
            Toast.makeText(this, "Virifier votre connexion Internet", Toast.LENGTH_LONG).show();
        } else {
            Utils.getClient().get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray trailersJsonResults = null;

                    try {
                        trailersJsonResults = response.getJSONArray("results");
                        trailers.addAll(Trailer.fromJSONArray(trailersJsonResults));
                        setUpLayout();
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

    private void setUpLayout() {

        TextView tvTitle = ButterKnife.findById(this, R.id.tv_title);
        tvTitle.setText(movie.getOriginalTitle());

        setUpVideoPlayer();
    }

    private void setUpVideoPlayer() {
        //trailers contains now all the trailers.
        //Let's randomly select the first one that is type 'Trailer'
        String selected = null;
        Trailer trailer;
        for (int i = 0; i < trailers.size() && selected == null; i++) {
            trailer = trailers.get(i);
            if (trailer.getTrailerType().equals("Trailer")) {
                selected = trailer.getTrailerID();
            }
        }

        final String trailerID = selected;
        if (trailerID != null) {

            YouTubePlayerView youTubePlayerView =
                    (YouTubePlayerView) findViewById(R.id.youtube_player);

            youTubePlayerView.initialize(Utils.getYouTubeAPIkey(),
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {
                            // do any work here to cue video, play video, etc.
                            youTubePlayer.setFullscreen(true);
                            youTubePlayer.loadVideo(trailerID);
                            // or to not play immediately call cueVideo instead
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });
        }

    }
}
