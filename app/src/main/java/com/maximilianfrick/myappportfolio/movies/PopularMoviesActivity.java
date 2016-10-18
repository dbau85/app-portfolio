package com.maximilianfrick.myappportfolio.movies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.core.BaseActivity;
import com.maximilianfrick.myappportfolio.core.dagger.Injector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularMoviesActivity extends BaseActivity {
    @BindView(R.id.view_movies)
    MoviesContract.View moviesView;
    MoviesContract.Presenter moviesPresenter;

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, PopularMoviesActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        Injector.getAppComponent().inject(this);
        moviesPresenter = new MoviesPresenter(moviesView);
        moviesPresenter.start();
    }
}
