package com.example.vuhoang.flicks.acitvity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.vuhoang.flicks.Api.InterfaceApi;
import com.example.vuhoang.flicks.Api.RetrofitClient;
import com.example.vuhoang.flicks.GetMovie.Movie;
import com.example.vuhoang.flicks.MainActivity;
import com.example.vuhoang.flicks.R;
import com.example.vuhoang.flicks.Trailers.GetTrailers;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rating extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private Movie movie;
    private YouTubePlayer player;

    @BindView(R.id.player)
    YouTubePlayerView playerView;

    @BindView(R.id.imgDetail)
    ImageView imgDetail;

    @BindView(R.id.title_detail)
    TextView title;

    @BindView(R.id.popularity)
    TextView popularity;

    @BindView(R.id.rating)
    RatingBar rate;

    @BindString(R.string.URL)
    String URL;

    @BindString(R.string.popularity)
    String pop;

    public static final String YOUTUBE_API_KEY = "AIzaSyBRUzRbT3E3YoeeG0Gc7_hG8u5pGV4EFKE";

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private GetTrailers listTrailers;

    private boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE);
        title.setText(movie.getTitle());
        pop += String.valueOf(movie.getPopularity());
        popularity.setText(pop);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                float rates = ratingBar.getRating();
            }
        });
        playerView.initialize(YOUTUBE_API_KEY,this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        this.player = youTubePlayer;
        this.player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                isFullScreen = b;
            }
        });
        getTrailer();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private void getTrailer(){
        RetrofitClient.getApi().getListTrailers(movie.getId(), InterfaceApi.Api_key).enqueue(new Callback<GetTrailers>() {
            @Override
            public void onResponse(Call<GetTrailers> call, Response<GetTrailers> response) {
                listTrailers = response.body();
                player.cueVideo(listTrailers.getYoutube().get(0).getSource());
            }

            @Override
            public void onFailure(Call<GetTrailers> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!isFullScreen)
            super.onBackPressed();
        else {
            isFullScreen = false;
            player.setFullscreen(false);
        }
    }

    private void setPlayVid(){
        if (movie.getPopularity() > movie.getVoteAverage()){}
    }
}
