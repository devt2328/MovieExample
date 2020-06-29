package com.dev.moviedemo.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.moviedemo.BaseViewHolder;
import com.dev.moviedemo.Interfaces.ClickListener;
import com.dev.moviedemo.Model.MovieList.ResultsItem;
import com.dev.moviedemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovielistAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {
    private static final String TAG = "MovielistAdapter";
    public Context mContext;
    public List<ResultsItem> mMovieList;
    private ClickListener mClickListener; // this listener for click
    public CustomFilter filter; //this var for text filter
    private String mSearchText; // this var for highlight
    private SpannableString spannable;


    public MovielistAdapter(Context context, List<ResultsItem> movieList, ClickListener clickListener) {
        mContext = context;
        mMovieList = movieList;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    public void updateData(List<ResultsItem> updatedMovieList) {
        this.mMovieList.addAll(updatedMovieList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(mMovieList, this);
        }
        return filter;
    }

    public class MovieViewHolder extends BaseViewHolder {
        private ImageView posterImg;
        private Button playBtn;
        private TextView movieTitle, releaseDate, overView, txtRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImg = itemView.findViewById(R.id.posterImg);
            playBtn = itemView.findViewById(R.id.playBtn);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            overView = itemView.findViewById(R.id.overView);
            txtRating = itemView.findViewById(R.id.txtRating);
        }

        @SuppressLint("SetTextI18n")
        public void onBind(int position) {
            super.onBind(position);
            ResultsItem resultsItem = mMovieList.get(position);
            String originalText = resultsItem.getTitle();
            SpannableString spannable = new SpannableString(originalText);
            if (mSearchText != null && !mSearchText.isEmpty()) {
                String[] searchWords = originalText.split(" ");
                String tracedString = "";
                for (String searchWord : searchWords) {
                    int startPos = originalText.toLowerCase(Locale.US).indexOf(mSearchText.toLowerCase(Locale.US), tracedString.length());
                    int endPos = startPos + mSearchText.length();
                    if (searchWord.length() >= mSearchText.length()) {
                        if (startPos != -1 && searchWord.toLowerCase(Locale.US).substring(0, mSearchText.length()).equals(mSearchText.toLowerCase(Locale.US))) {
                            ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.RED});
                            TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);
                            spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else {
                        spannable = SpannableString.valueOf(originalText);
                    }
                    tracedString = tracedString + " " + searchWord;
                }
            }
            movieTitle.setText(spannable);
            txtRating.setText("" + resultsItem.getVoteAverage() + " " + mContext.getResources().getString(R.string.star));
            releaseDate.setText(resultsItem.getReleaseDate());
            overView.setText(resultsItem.getOverview());
            Glide.with(mContext)
                    .load(mContext.getResources().getString(R.string.POSTER_URL) + resultsItem.getPosterPath())
                    .fitCenter()
                    .placeholder(R.drawable.place_holder)
                    .into(posterImg);

            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(mMovieList.get(getAdapterPosition()));
                }
            });
        }
    }

    /*Filter class for searchView*/
    public class CustomFilter extends Filter {

        MovielistAdapter adapter;
        List<ResultsItem> filterList;

        public CustomFilter(List<ResultsItem> filterList, MovielistAdapter adapter) {
            this.adapter = adapter;
            this.filterList = filterList;

        }

        //FILTERING OCCURS
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            //CHECK CONSTRAINT VALIDITY
            if (constraint != null && constraint.length() > 0) {
                //CHANGE TO LOWER
                String prefixString = constraint.toString().toLowerCase();
                mSearchText = prefixString;
                //STORE OUR FILTERED PLAYERS
                ArrayList<ResultsItem> filteredPlayers = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {
                    //CHECK
                    final Object value = filterList.get(i).getTitle();
                    if (filterObject(value, prefixString)) {
                        filteredPlayers.add(filterList.get(i));
                    }
                }
                results.count = filteredPlayers.size();
                results.values = filteredPlayers;
            } else {
                mSearchText = "";
                results.count = filterList.size();
                results.values = filterList;

            }
            return results;
        }

        protected boolean filterObject(Object myObject, String constraint) {
            final String valueText = myObject.toString().toLowerCase();
            // First match against the whole, non-splitted value
            if (valueText.startsWith(constraint)) {
                return true;
            } else {
                final String[] words = valueText.split(" ");
                final int wordCount = words.length;

                // Start at index 0, in case valueText starts with space(s)
                for (int k = 0; k < wordCount; k++) {
                    if (words[k].startsWith(constraint)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.mMovieList = (List<ResultsItem>) results.values;
            //REFRESH
            adapter.notifyDataSetChanged();
        }
    }
}