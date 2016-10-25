package com.maximilianfrick.myappportfolio.movies.detail;

import android.support.annotation.NonNull;

import com.maximilianfrick.myappportfolio.movies.models.Movie;

import static dagger.internal.Preconditions.checkNotNull;

public class MoviesDetailPresenter implements MoviesDetailContract.Presenter {
    private MoviesDetailContract.View view;
    private Movie movie;

    public MoviesDetailPresenter(MoviesDetailContract.View moviesDetailView, Movie movie) {
        this.view = checkNotNull(moviesDetailView, "View may not be null!");
        this.movie = checkNotNull(movie, "Movie may not be null!");
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.showMovieDetails(movie);
    }
}
