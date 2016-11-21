package com.maximilianfrick.myappportfolio.movies.detail;

import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.MoviesService;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.ReviewsData;
import com.maximilianfrick.myappportfolio.movies.models.TrailersData;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import static dagger.internal.Preconditions.checkNotNull;

public class MoviesDetailPresenter implements MoviesDetailContract.Presenter {

    private MoviesDetailContract.View view;
    private Movie movie;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Inject
    MoviesService moviesService;
    @Inject
    MovieFavoritesController movieFavoritesController;

    public MoviesDetailPresenter(MoviesDetailContract.View moviesDetailView, Movie movie) {
        Injector.getAppComponent().inject(this);
        this.view = checkNotNull(moviesDetailView, "View may not be null!");
        this.movie = movie;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        if (movie == null) {
            return;
        }
        handleFavoriteStatus(movie);
        view.showMovieDetails(movie);
        loadTrailers();
        loadReviews();
    }

    private void handleFavoriteStatus(Movie movie) {
        this.movie.setFavorite(movieFavoritesController.isFavorite(movie));
    }

    public void loadMovie(Movie movie) {
        this.movie = movie;
        start();
    }

    private void loadTrailers() {
        subscriptions.add(moviesService.getTrailers(movie.getId()).observeOn(AndroidSchedulers.mainThread())
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
                }));
    }

    private void loadReviews() {
        subscriptions.add(moviesService.getReviews(movie.getId()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ReviewsData>() {
            @Override
            public void call(ReviewsData reviewsData) {
                view.showReviews(reviewsData.getReviews());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                view.showErrorNoInternet();
            }
        }));
    }

    @Override
    public void onPause() {
        subscriptions.unsubscribe();
    }

    @Override
    public void addToFavorites(boolean isFavorite) {
        if (isFavorite) {
            movieFavoritesController.addMovieToFavorites(movie);
        } else {
            movieFavoritesController.deleteMovieFromFavorites(movie);
        }
    }
}
