package com.dev.moviedemo.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.moviedemo.Model.MovieCredit.CrewItem;
import com.dev.moviedemo.R;

import java.util.List;

public class CrewlistAdapter extends RecyclerView.Adapter<CrewlistAdapter.MovieViewHolder> {
    private static final String TAG = "CrewlistAdapter";
    private Context mContext;
    private List<CrewItem> mCrewList;
    private RequestOptions requestOption;

    public CrewlistAdapter(Context context, List<CrewItem> crewList) {
        mContext = context;
        mCrewList = crewList;
    }

    public void updateData(List<CrewItem> updatedMovieList) {
        this.mCrewList.addAll(updatedMovieList);
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
        Log.i(TAG, "onBindViewHolder: " + mCrewList.get(position).getName());
        CrewItem resultsItem = mCrewList.get(position);
        holder.genresname.setText(resultsItem.getName());
        holder.genresJob.setText(resultsItem.getJob());
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
        return mCrewList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImg;
        private TextView genresname, genresJob;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImg = itemView.findViewById(R.id.posterImg);
            genresname = itemView.findViewById(R.id.genresName);
            genresJob = itemView.findViewById(R.id.genresOriginalName);
        }
    }
}
