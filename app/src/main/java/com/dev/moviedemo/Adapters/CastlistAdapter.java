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
import com.dev.moviedemo.Model.MovieCredit.CastItem;
import com.dev.moviedemo.R;

import java.util.List;

public class CastlistAdapter extends RecyclerView.Adapter<CastlistAdapter.MovieViewHolder> {
    private static final String TAG = "CastlistAdapter";
    private Context mContext;
    private List<CastItem> mCastList;

    public CastlistAdapter(Context context, List<CastItem> castList) {
        mContext = context;
        mCastList = castList;
    }

    public void updateData(List<CastItem> updatedMovieList) {
        this.mCastList.addAll(updatedMovieList);
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
        CastItem resultsItem = mCastList.get(position);
        holder.genresname.setText(resultsItem.getName());
        holder.genresCharacter.setText(resultsItem.getCharacter());
        RequestOptions requestOption;
        if (resultsItem.getProfilePath() == null) {
            requestOption = new RequestOptions()
                    .placeholder(R.drawable.place_holder).fitCenter();
        } else {
            requestOption = new RequestOptions()
                    .placeholder(R.drawable.place_holder).centerCrop();
        }

        Glide.with(mContext)
                .load(Uri.parse(mContext.getResources().getString(R.string.POSTER_URL) + resultsItem.getProfilePath()))
                .apply(requestOption)
                .into(holder.posterImg);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mCastList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImg;
        private TextView genresname, genresCharacter;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImg = itemView.findViewById(R.id.posterImg);
            genresCharacter = itemView.findViewById(R.id.genresOriginalName);
            genresname = itemView.findViewById(R.id.genresName);
        }
    }
}