package com.maximilianfrick.myappportfolio.movies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.core.BaseActivity;
import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.MoviesData;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class PopularMoviesActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @Inject
    MoviesService moviesService;
    private ArrayList<String> images;
    private MovieListAdapter adapter;

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, PopularMoviesActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        Injector.getAppComponent().inject(this);
        images = new ArrayList<>();
        Observable<MoviesData> observable = moviesService.getPopularMovies();
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MoviesData>() {
            @Override
            public void call(MoviesData moviesData) {
                for (Movie movie : moviesData.getMovies()) {
                    images.add(PopularMoviesActivity.this.getString(R.string.base_url_images) + movie.getPosterPath());
                }
                adapter.setItems(images);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });


        //TODO: get Data and replace mock here
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieListAdapter(this, images);
        recyclerView.setAdapter(adapter);

    }
}
