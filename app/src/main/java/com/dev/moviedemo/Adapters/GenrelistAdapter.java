package com.dev.moviedemo.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.moviedemo.Model.MovieDetails.GenresItem;
import com.dev.moviedemo.R;

import java.util.List;

public class GenrelistAdapter extends RecyclerView.Adapter<GenrelistAdapter.MovieViewHolder> {
    private static final String TAG = "GenrelistAdapter";
    private Context mContext;
    private List<GenresItem> mGenresList;

    public GenrelistAdapter(Context context, List<GenresItem> genresList) {
        mContext = context;
        mGenresList = genresList;
    }

    public void updateData(List<GenresItem> updatedMovieList) {
        this.mGenresList.addAll(updatedMovieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genres_item_new, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: " + mGenresList.get(position).getName());
        GenresItem resultsItem = mGenresList.get(position);
        holder.movieDetailsGenre.setText(resultsItem.getName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mGenresList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView movieDetailsGenre;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieDetailsGenre = itemView.findViewById(R.id.movieDetailsGenre);
        }
    }
}
