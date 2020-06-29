package com.dev.moviedemo.Model.MovieVideos;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMovieVideos{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<ResultsItem> results;

	public int getId(){
		return id;
	}

	public List<ResultsItem> getResults(){
		return results;
	}
}