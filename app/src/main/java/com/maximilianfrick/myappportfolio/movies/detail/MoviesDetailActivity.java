package com.maximilianfrick.myappportfolio.movies.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.models.Movie;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesDetailActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_MOVIE = "KEY_EXTRA_MOVIE";
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.view_movies_detail)
    MoviesDetailContract.View detailView;
    private MoviesDetailContract.Presenter detailPresenter;

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MoviesDetailActivity.class);
        intent.putExtra(KEY_EXTRA_MOVIE, Parcels.wrap(movie));
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        ButterKnife.bind(this);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(KEY_EXTRA_MOVIE));

        initToolbar(movie);

        detailPresenter = new MoviesDetailPresenter(detailView, movie);
    }

    private void initToolbar(Movie movie) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String itemTitle = movie.getOriginalTitle();
        collapsingToolbarLayout.setTitle(itemTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailPresenter.start();
    }
}
