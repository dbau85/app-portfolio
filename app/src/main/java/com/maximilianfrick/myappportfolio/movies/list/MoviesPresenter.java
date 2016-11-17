package com.maximilianfrick.myappportfolio.movies.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.MoviesService;
import com.maximilianfrick.myappportfolio.movies.models.MoviesData;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static dagger.internal.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {

    @Inject
    MoviesService moviesService;
    private final MoviesContract.View moviesView;
    private MoviesFilterType filterType = MoviesFilterType.POPULAR_MOVIES;
    private Subscription subscription;

    @Override
    public void start() {
        loadMovies();
    }

    @Override
    public void onPause() {
        if (subscription != null &&  !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public MoviesPresenter(@NonNull MoviesContract.View view) {
        Injector.getAppComponent().inject(this);
        moviesView = checkNotNull(view, "View cannot be null!");
        moviesView.setPresenter(this);
    }

    @Override
    public void loadMovies() {
        Observable<MoviesData> moviesDataObservable;
        if (filterType == MoviesFilterType.POPULAR_MOVIES) {
            moviesDataObservable = moviesService.getPopularMovies();
        } else {
            moviesDataObservable = moviesService.getTopRatedMovies();
        }
        subscription = moviesDataObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MoviesData>() {
                    @Override
                    public void call(MoviesData moviesData) {
                        moviesView.showMovies(moviesData.getMovies());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        moviesView.showErrorNoInternet();
                        Log.d(getClass().getSimpleName(), "loadMovies: ", throwable);
                    }
                });
    }

    @Override
    public MoviesFilterType getFilterType() {
        return filterType;
    }

    @Override
    public void setFilterType(MoviesFilterType filterType) {
        this.filterType = filterType;
    }

}
