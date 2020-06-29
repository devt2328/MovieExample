package com.dev.moviedemo.Model.MovieCredit;

import com.google.gson.annotations.SerializedName;

public class CrewItem{

	@SerializedName("gender")
	private int gender;

	@SerializedName("credit_id")
	private String creditId;

	@SerializedName("name")
	private String name;

	@SerializedName("profile_path")
	private Object profilePath;

	@SerializedName("id")
	private int id;

	@SerializedName("department")
	private String department;

	@SerializedName("job")
	private String job;

	public int getGender(){
		return gender;
	}

	public String getCreditId(){
		return creditId;
	}

	public String getName(){
		return name;
	}

	public Object getProfilePath(){
		return profilePath;
	}

	public int getId(){
		return id;
	}

	public String getDepartment(){
		return department;
	}

	public String getJob(){
		return job;
	}
}