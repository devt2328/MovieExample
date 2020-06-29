package com.dev.moviedemo.RetrofitApi;

import com.dev.moviedemo.Model.MovieCredit.ResponseMovieCredit;
import com.dev.moviedemo.Model.MovieDetails.ResponseMovieDetails;
import com.dev.moviedemo.Model.MovieList.ResponseMovieList;
import com.dev.moviedemo.Model.MovieSimilar.ResponseMovieSimilar;
import com.dev.moviedemo.Model.MovieVideos.ResponseMovieVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoints {
    @GET("/3/movie/now_playing")
    Call<ResponseMovieList> movieList(@Query("api_key") String apiKey,
                                      @Query("language") String language,
                                      @Query("page") int pageNum);

    @GET("/3/movie/{movie_id}")
    Call<ResponseMovieDetails> movieDetails(@Path(value = "movie_id", encoded = true) int movieId,
                                            @Query("api_key") String apiKey,
                                            @Query("language") String language);

    @GET("/3/movie/{movie_id}/credits")
    Call<ResponseMovieCredit> movieCredit(@Path(value = "movie_id", encoded = true) int movieId,
                                          @Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}/videos")
    Call<ResponseMovieVideos> movieVideos(@Path(value = "movie_id", encoded = true) int movieId,
                                          @Query("api_key") String apiKey, @Query("language") String language);

    @GET("/3/movie/{movie_id}/similar")
    Call<ResponseMovieSimilar> movieSimilar(@Path(value = "movie_id", encoded = true) int movieId,
                                            @Query("api_key") String apiKey,
                                            @Query("language") String language,
                                            @Query("page") int pageNum);
}
