package com.example.vuhoang.flicks.acitvity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
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

    @BindView(R.id.player) YouTubePlayerView playerView;

    @BindView(R.id.error) TextView error;

    @BindView(R.id.title_detail) TextView title;

    @BindView(R.id.rating) AppCompatRatingBar rate;

    @BindView(R.id.releaseDate) TextView releaseDate;

    @BindView(R.id.popularity) TextView popularity;

    @BindView(R.id.adult) TextView adult;

    @BindView(R.id.vote_average) TextView vote;

    @BindView(R.id.overViewDetail) TextView overView;

    @BindString(R.string.URL) String URL;

    @BindString(R.string.popularity) String pop;

    @BindString(R.string.release_date) String release;

    @BindString(R.string.adult) String isAdult;

    @BindString(R.string.voteAvg) String voteAvg;

    @BindString(R.string.API_KEY_YOUTUBE) String YOUTUBE_API_KEY;

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

    private boolean isPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE);
        isPopular = getIntent().getBooleanExtra(MainActivity.IS_POPULAR,true);

        title.setText(movie.getTitle());

        release += movie.getReleaseDate();
        releaseDate.setText(release);

        pop += String.valueOf(movie.getPopularity());
        popularity.setText(pop);

        isAdult += movie.getAdult()?"Yes":"No";
        adult.setText(isAdult);

        voteAvg += String.valueOf(movie.getVoteAverage());
        vote.setText(voteAvg);

        overView.setText(movie.getOverview());

        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
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
                if (listTrailers.getYoutube().size()!= 0)
                    if (isPopular)
                        player.loadVideo(listTrailers.getYoutube().get(0).getSource());
                    else
                        player.cueVideo(listTrailers.getYoutube().get(0).getSource());
                else
                    error.setVisibility(View.VISIBLE);
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


}
