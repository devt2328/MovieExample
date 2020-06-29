package com.dev.moviedemo;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Constants {
    public static final String LANGUAGE = "en-US";
    public static final String YOUTUBE_BASEUL = "https://www.youtube.com/watch?v=";

    public static void hideKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
