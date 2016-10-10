package com.maximilianfrick.myappportfolio.movies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.core.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularMoviesActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, PopularMoviesActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        List<Uri> images = new ArrayList<>();
        //TODO: get Data and replace mock here
        images.add(Uri.parse("http://image.tmdb.org/t/p/w185/811DjJTon9gD6hZ8nCjSitaIXFQ.jpg"));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MovieListAdapter adapter = new MovieListAdapter(this, images);
        recyclerView.setAdapter(adapter);
    }
}
