package com.maximilianfrick.myappportfolio.core;

import android.app.Activity;
import android.content.Intent;

import com.maximilianfrick.myappportfolio.movies.PopularMoviesActivity;

public class AppFlowController {

    public void onProjectMoviesPressed(Activity activity) {
        openActivity(activity, PopularMoviesActivity.newIntent(activity));
    }

    private void openActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }
}
