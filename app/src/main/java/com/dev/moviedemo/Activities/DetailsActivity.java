package com.dev.moviedemo.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.moviedemo.Adapters.CastlistAdapter;
import com.dev.moviedemo.Adapters.CrewlistAdapter;
import com.dev.moviedemo.Adapters.GenrelistAdapter;
import com.dev.moviedemo.Adapters.MovieSimilarAdapter;
import com.dev.moviedemo.Model.MovieCredit.CastItem;
import com.dev.moviedemo.Model.MovieCredit.CrewItem;
import com.dev.moviedemo.Model.MovieCredit.ResponseMovieCredit;
import com.dev.moviedemo.Model.MovieDetails.GenresItem;
import com.dev.moviedemo.Model.MovieDetails.ResponseMovieDetails;
import com.dev.moviedemo.Model.MovieSimilar.ResponseMovieSimilar;
import com.dev.moviedemo.Model.MovieSimilar.ResultsItem;
import com.dev.moviedemo.Model.MovieVideos.ResponseMovieVideos;
import com.dev.moviedemo.R;
import com.dev.moviedemo.RetrofitApi.APIClient;
import com.dev.moviedemo.RetrofitApi.ApiEndPoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.moviedemo.Constants.LANGUAGE;
import static com.dev.moviedemo.Utils.movieDetailsTextReleaseDate;
import static com.dev.moviedemo.Utils.movieDetailsTextTitle;
import static com.dev.moviedemo.Utils.movieId;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";
    private ImageView imgPosterDetails;
    private TextView movieDetailsTitle;
    private TextView movieDetailsReleaseDate;
    private TextView movieDetailsSynopsisInner;
    private TextView movieDetailsGenre;
    private TextView txtSimilarVideos;
    private TextView txtCrew;
    private TextView movieDetailsCast;
    private TextView movieDetailsSynopsis;
    private ApiEndPoints apiEndPoints;
    private MovieSimilarAdapter movieSimilarAdapter;
    private CastlistAdapter castlistAdapter;
    private CrewlistAdapter crewlistAdapter;
    private GenrelistAdapter genrelistAdapter;
    private List<CastItem> listCast;
    private List<CrewItem> listCrew;
    private List<ResultsItem> listSimilar;
    private List<GenresItem> listgenres;
    private String mLanguage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        apiEndPoints = APIClient.getClient().create(ApiEndPoints.class);
        movieDetailsCast = findViewById(R.id.movieDetailsCast);
        movieDetailsSynopsis = findViewById(R.id.movieDetailsSynopsis);
        txtCrew = findViewById(R.id.txtCrew);
        txtSimilarVideos = findViewById(R.id.txtSimilarVideos);
        movieDetailsGenre = findViewById(R.id.movieDetailsGenre);
        imgPosterDetails = findViewById(R.id.imgPosterDetails);
        movieDetailsTitle = findViewById(R.id.movieDetailsTitle);
        movieDetailsTitle.setText(movieDetailsTextTitle);
        movieDetailsReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        movieDetailsReleaseDate.setText(movieDetailsTextReleaseDate);
        movieDetailsSynopsisInner = findViewById(R.id.movieDetailsSynopsisInner);
        RecyclerView castRecyclerView = findViewById(R.id.castRecyclerView);
        RecyclerView crewRecyclerView = findViewById(R.id.crewRecyclerView);
        RecyclerView videosRecyclerView = findViewById(R.id.videosRecyclerView);
        RecyclerView genreRecyclerView = findViewById(R.id.genreRecyclerView);

        listCast = new ArrayList<>();
        listCrew = new ArrayList<>();
        listSimilar = new ArrayList<>();
        listgenres = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this, RecyclerView.HORIZONTAL, false);
        castRecyclerView.setLayoutManager(layoutManager);
        castlistAdapter = new CastlistAdapter(this, listCast);
        castRecyclerView.setAdapter(castlistAdapter);

        crewRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        crewlistAdapter = new CrewlistAdapter(this, listCrew);
        crewRecyclerView.setAdapter(crewlistAdapter);

        videosRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        movieSimilarAdapter = new MovieSimilarAdapter(this, listSimilar);
        videosRecyclerView.setAdapter(movieSimilarAdapter);

        genreRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        genrelistAdapter = new GenrelistAdapter(this, listgenres);
        genreRecyclerView.setAdapter(genrelistAdapter);
        movieDetailsApiCall();
    }

    private void movieDetailsApiCall() {
        Call<ResponseMovieDetails> movieListCall = apiEndPoints.movieDetails(movieId, getResources().getString(R.string.API_KEY), LANGUAGE);
        movieListCall.enqueue(new Callback<ResponseMovieDetails>() {
            @Override
            public void onResponse(Call<ResponseMovieDetails> call, Response<ResponseMovieDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listgenres = response.body().getGenres();
                    mLanguage = response.body().getOriginalLanguage();
                    movieDetailsTitle.setText(response.body().getTitle());
                    movieDetailsReleaseDate.setText(response.body().getReleaseDate());
                    Glide.with(DetailsActivity.this)
                            .load(getResources().getString(R.string.POSTER_URL) + response.body().getPosterPath())
                            .centerCrop()
                            .placeholder(R.drawable.place_holder)
                            .into(imgPosterDetails);
                    movieDetailsSynopsisInner.setText(response.body().getOverview());
                    if (listgenres != null && listgenres.size() > 0) {
                        genrelistAdapter.updateData(listgenres);
                        movieDetailsGenre.setVisibility(View.VISIBLE);
                    } else {
                        movieDetailsGenre.setVisibility(View.GONE);
                    }
                    movieCastApiCall();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovieDetails> call, Throwable t) {

            }
        });
    }

    private void movieCastApiCall() {
        Call<ResponseMovieCredit> movieListCall = apiEndPoints.movieCredit(movieId, getResources().getString(R.string.API_KEY));
        movieListCall.enqueue(new Callback<ResponseMovieCredit>() {
            @Override
            public void onResponse(Call<ResponseMovieCredit> call, Response<ResponseMovieCredit> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listCast = response.body().getCast();
                    listCrew = response.body().getCrew();
                    if (listCast != null && listCast.size() > 0) {
                        castlistAdapter.updateData(listCast);
                        movieDetailsCast.setVisibility(View.VISIBLE);
                    } else {
                        movieDetailsCast.setVisibility(View.GONE);
                    }
                    if (listCrew != null && listCrew.size() > 0) {
                        crewlistAdapter.updateData(listCrew);
                        txtCrew.setVisibility(View.VISIBLE);
                    } else {
                        txtCrew.setVisibility(View.GONE);
                    }

                    movieSimilarApiCall();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovieCredit> call, Throwable t) {

            }
        });
    }

    private void movieSimilarApiCall() {
        Call<ResponseMovieSimilar> movieListCall = apiEndPoints.movieSimilar(movieId, getResources().getString(R.string.API_KEY), mLanguage, 1);
        movieListCall.enqueue(new Callback<ResponseMovieSimilar>() {
            @Override
            public void onResponse(Call<ResponseMovieSimilar> call, Response<ResponseMovieSimilar> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listSimilar = response.body().getResults();
                    if (listSimilar != null && listSimilar.size() > 0) {
                        movieSimilarAdapter.updateData(listSimilar);
                        txtSimilarVideos.setVisibility(View.VISIBLE);
                    } else {
                        txtSimilarVideos.setVisibility(View.GONE);
                    }
                }
                videosApiCall();
            }

            @Override
            public void onFailure(Call<ResponseMovieSimilar> call, Throwable t) {

            }
        });
    }

    private void videosApiCall() {
        Call<ResponseMovieVideos> movieListCall = apiEndPoints.movieVideos(movieId, getResources().getString(R.string.API_KEY), mLanguage);
        movieListCall.enqueue(new Callback<ResponseMovieVideos>() {
            @Override
            public void onResponse(Call<ResponseMovieVideos> call, Response<ResponseMovieVideos> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<ResponseMovieVideos> call, Throwable t) {

            }
        });
    }
}