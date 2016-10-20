package com.maximilianfrick.myappportfolio.movies.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.models.Movie;

import org.parceler.Parcels;

public class MoviesDetailActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_MOVIE = "KEY_EXTRA_MOVIE";

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MoviesDetailActivity.class);
        intent.putExtra(KEY_EXTRA_MOVIE, Parcels.wrap(movie));
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(KEY_EXTRA_MOVIE));
    }
}
