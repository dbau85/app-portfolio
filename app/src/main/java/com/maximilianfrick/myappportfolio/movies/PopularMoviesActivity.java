package com.maximilianfrick.myappportfolio.movies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.core.BaseActivity;
import com.maximilianfrick.myappportfolio.core.dagger.Injector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularMoviesActivity extends BaseActivity {
    private static final String KEY_FILTER_TYPE = "KEY_FILTER_TYPE";
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

        if (savedInstanceState != null) {
            moviesPresenter.setFilterType((MoviesFilterType) savedInstanceState.getSerializable(KEY_FILTER_TYPE));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        moviesPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                moviesView.showFilteringOptions();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_FILTER_TYPE, moviesPresenter.getFilterType());
        super.onSaveInstanceState(outState);
    }
}
