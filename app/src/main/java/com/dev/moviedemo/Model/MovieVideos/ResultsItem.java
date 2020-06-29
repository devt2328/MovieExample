package com.dev.moviedemo.Model.MovieVideos;

import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("site")
	private String site;

	@SerializedName("size")
	private int size;

	@SerializedName("iso_3166_1")
	private String iso31661;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	@SerializedName("iso_639_1")
	private String iso6391;

	@SerializedName("key")
	private String key;

	public String getSite(){
		return site;
	}

	public int getSize(){
		return size;
	}

	public String getIso31661(){
		return iso31661;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public String getIso6391(){
		return iso6391;
	}

	public String getKey(){
		return key;
	}
}