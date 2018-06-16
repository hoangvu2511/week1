package com.example.vuhoang.flicks.Api;

import com.example.vuhoang.flicks.GetMovie.ListMovies;
import com.example.vuhoang.flicks.Trailers.GetTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterfaceApi {
    String Api_key = "9fbb07c63e4fbbabdb479e11f6dd9f33";
    String Base_URL = "http://api.themoviedb.org";
    String BASE_IMAGES_URL = "http://image.tmdb.org/t/p/";

    @GET("/3/movie/now_playing")
    Call<ListMovies> getNowPlaying(@Query("api_key") String API_KEY,
                                   @Query("page") int page,
                                   @Query("language") String language);

    @GET("/3/movie/{id}/trailers")
    Call<GetTrailers> getListTrailers(@Path("id") int id, @Query("api_key") String api);


}
