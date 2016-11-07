package com.maximilianfrick.myappportfolio.movies.detail;

import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.MoviesService;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.TrailersData;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static dagger.internal.Preconditions.checkNotNull;

public class MoviesDetailPresenter implements MoviesDetailContract.Presenter {

    private MoviesDetailContract.View view;
    private Movie movie;

    @Inject
    MoviesService moviesService;

    public MoviesDetailPresenter(MoviesDetailContract.View moviesDetailView, Movie movie) {
        Injector.getAppComponent().inject(this);
        this.view = checkNotNull(moviesDetailView, "View may not be null!");
        this.movie = checkNotNull(movie, "Movie may not be null!");
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.showMovieDetails(movie);
        loadTrailersByMovieId();
    }

    @Override
    public void loadTrailersByMovieId() {
        moviesService.getTrailers(movie.getId()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TrailersData>() {
                    @Override
                    public void call(TrailersData trailersData) {
                        view.showTrailerList(trailersData.getTrailers());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showErrorNoInternet();
                    }
                });
    }

    @Override
    public void setToFavorites(Movie movie) {

    }
}
