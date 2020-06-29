package com.dev.moviedemo.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.moviedemo.Adapters.MovielistAdapter;
import com.dev.moviedemo.Interfaces.ClickListener;
import com.dev.moviedemo.Model.MovieList.ResponseMovieList;
import com.dev.moviedemo.Model.MovieList.ResultsItem;
import com.dev.moviedemo.MySuggestionProvider;
import com.dev.moviedemo.R;
import com.dev.moviedemo.RetrofitApi.APIClient;
import com.dev.moviedemo.RetrofitApi.ApiEndPoints;
import com.dev.moviedemo.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.moviedemo.Constants.LANGUAGE;

public class MainActivity extends AppCompatActivity implements ClickListener {
    private MovielistAdapter mMovielistAdapter;
    public List<ResultsItem> mMovieList;
    private ApiEndPoints mApiEndPoints;
    private SearchRecentSuggestions suggestions;
    private MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiEndPoints = APIClient.getClient().create(ApiEndPoints.class);
        RecyclerView movieRecyclerView = findViewById(R.id.movieRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        movieRecyclerView.setLayoutManager(layoutManager);
        mMovieList = new ArrayList<>();
        /*Set Adapter with list of movies
        @mMovieList List fo movies.*/

        mMovielistAdapter = new MovielistAdapter(this, mMovieList, this);
        movieRecyclerView.setAdapter(mMovielistAdapter);
        /*Api call for listing movies*/
        ApiCall();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        //SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Tells your app's SearchView to use this activity's searchable configuration
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryRefinementEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(MainActivity.this,
                        MySuggestionProvider.AUTHORITY,
                        MySuggestionProvider.MODE);

                suggestions.saveRecentQuery(query, null);
                // -------------------------------------------------------
                mMovielistAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                mMovielistAdapter.getFilter().filter(query);
                return false;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                CursorAdapter selectedView = searchView.getSuggestionsAdapter();
                Cursor cursor = (Cursor) selectedView.getItem(position);
                int index = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1);
                searchView.setQuery(cursor.getString(index), true);
                return true;
            }
        });
        return true;
    }

    private void ApiCall() {
        int currentPage = 1;
        Call<ResponseMovieList> movieListCall = mApiEndPoints.movieList(getResources().getString(R.string.API_KEY), LANGUAGE, currentPage);
        movieListCall.enqueue(new Callback<ResponseMovieList>() {
            @Override
            public void onResponse(Call<ResponseMovieList> call, final Response<ResponseMovieList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mMovieList.addAll(response.body().getResults());
                    mMovielistAdapter.updateData(mMovieList);
                }
            }

            @Override
            public void onFailure(Call<ResponseMovieList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(ResultsItem resultsItem) {
        Utils.movieId = resultsItem.getId();
        Utils.movieDetailsTextTitle = resultsItem.getTitle();
        Utils.movieDetailsTextReleaseDate = resultsItem.getReleaseDate();
        Utils.movieDetailsTextOverview = resultsItem.getOverview();
        startActivity(new Intent(MainActivity.this, DetailsActivity.class));
    }
}