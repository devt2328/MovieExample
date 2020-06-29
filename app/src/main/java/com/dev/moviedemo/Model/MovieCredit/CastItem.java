package com.dev.moviedemo.Model.MovieCredit;

import com.google.gson.annotations.SerializedName;

public class CastItem{

	@SerializedName("cast_id")
	private int castId;

	@SerializedName("character")
	private String character;

	@SerializedName("gender")
	private int gender;

	@SerializedName("credit_id")
	private String creditId;

	@SerializedName("name")
	private String name;

	@SerializedName("profile_path")
	private String profilePath;

	@SerializedName("id")
	private int id;

	@SerializedName("order")
	private int order;

	public int getCastId(){
		return castId;
	}

	public String getCharacter(){
		return character;
	}

	public int getGender(){
		return gender;
	}

	public String getCreditId(){
		return creditId;
	}

	public String getName(){
		return name;
	}

	public String getProfilePath(){
		return profilePath;
	}

	public int getId(){
		return id;
	}

	public int getOrder(){
		return order;
	}
}