package com.dev.moviedemo.Model;

public class Model {

    public static final int CAST_TYPE = 0;
    public static final int CREW_TYPE = 1;
    public static final int SIMILAR_VIDEOS_TYPE = 2;
    public static final int VIDEOS_TYPE = 3;

    public int type;
    public int data;
    public String text;

    public Model(int type, String text, int data) {
        this.type = type;
        this.data = data;
        this.text = text;
    }
}
