package com.dev.moviedemo.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.moviedemo.Model.MovieSimilar.ResultsItem;
import com.dev.moviedemo.R;

import java.util.List;

public class MovieSimilarAdapter extends RecyclerView.Adapter<MovieSimilarAdapter.MovieViewHolder> {
    private static final String TAG = "MovieSimilarAdapter";
    private Context mContext;
    private List<ResultsItem> mSimilarList;
    private RequestOptions requestOption;

    public MovieSimilarAdapter(Context context, List<ResultsItem> genresList) {
        mContext = context;
        mSimilarList = genresList;
    }

    public void updateData(List<ResultsItem> updatedMovieList) {
        this.mSimilarList.addAll(updatedMovieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genres_item, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        ResultsItem resultsItem = mSimilarList.get(position);
        holder.genresname.setText(resultsItem.getTitle());
        holder.genresOriginalName.setText("" + resultsItem.getVoteAverage() + " " + mContext.getResources().getString(R.string.star));
        if (resultsItem.getPosterPath() == null) {
            requestOption = new RequestOptions()
                    .placeholder(R.drawable.place_holder).fitCenter();
        } else {
            requestOption = new RequestOptions()
                    .placeholder(R.drawable.place_holder).centerCrop();
        }

        Glide.with(mContext)
                .load(Uri.parse(mContext.getResources().getString(R.string.POSTER_URL) + resultsItem.getPosterPath()))
                .apply(requestOption)
                .into(holder.posterImg);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mSimilarList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImg;
        private TextView genresname, genresOriginalName;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImg = itemView.findViewById(R.id.posterImg);
            genresname = itemView.findViewById(R.id.genresName);
            genresOriginalName = itemView.findViewById(R.id.genresOriginalName);
        }
    }
}
