package com.maximilianfrick.myappportfolio.movies;

import android.support.annotation.NonNull;

import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.MoviesData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

import static dagger.internal.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {

    @Inject
    MoviesService moviesService;
    private List<String> moviePosterPaths;
    private final MoviesContract.View moviesView;
    private MoviesFilterType filterType = MoviesFilterType.POPULAR_MOVIES;

    @Override
    public void start() {
        loadMovies();
    }

    public MoviesPresenter(@NonNull MoviesContract.View view) {
        Injector.getAppComponent().inject(this);
        moviesView = checkNotNull(view, "View cannot be null!");
        moviesView.setPresenter(this);
    }

    @Override
    public void loadMovies() {
        moviePosterPaths = new ArrayList<>();
        Observable<MoviesData> moviesDataObservable;
        if (filterType == MoviesFilterType.POPULAR_MOVIES) {
            moviesDataObservable = moviesService.getPopularMovies();
        } else {
            moviesDataObservable = moviesService.getTopRatedMovies();
        }
        moviesDataObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MoviesData>() {
                    @Override
                    public void call(MoviesData moviesData) {
                        for (Movie movie : moviesData.getMovies()) {
                            moviePosterPaths.add(movie.getPosterPath());
                        }
                        moviesView.showMovies(moviePosterPaths);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e(throwable);
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
