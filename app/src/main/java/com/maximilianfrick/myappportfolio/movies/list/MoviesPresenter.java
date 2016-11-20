package com.maximilianfrick.myappportfolio.movies.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.MoviesService;
import com.maximilianfrick.myappportfolio.movies.detail.MovieFavoritesController;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.MoviesData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

import static dagger.internal.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {

    @Inject
    MoviesService moviesService;
    @Inject
    MovieFavoritesController movieFavoritesController;
    private final MoviesContract.View moviesView;
    private MoviesFilterType filterType = MoviesFilterType.POPULAR_MOVIES;
    private Subscription subscription;

    @Override
    public void start() {
        loadMovies();
    }

    @Override
    public void onPause() {
        if (subscription != null && !subscription.isUnsubscribed()) {
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
        Observable<List<Movie>> moviesDataObservable = getMoviesDataObservable();
        subscription = moviesDataObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> movieList) {
                        moviesView.showMovies(movieList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        moviesView.showErrorNoInternet();
                        Log.d(getClass().getSimpleName(), "loadMovies: ", throwable);
                    }
                });
    }

    private Observable<List<Movie>> getMoviesDataObservable() {
        Observable<List<Movie>> moviesDataObservable;
        switch (filterType) {
            case POPULAR_MOVIES:
                moviesDataObservable = moviesService.getPopularMovies().map(new Func1<MoviesData, List<Movie>>() {
                    @Override
                    public List<Movie> call(MoviesData moviesData) {
                        return moviesData.getMovies();
                    }
                });
                break;
            case TOP_RATED_MOVIES:
                moviesDataObservable = moviesService.getTopRatedMovies().map(new Func1<MoviesData, List<Movie>>() {
                    @Override
                    public List<Movie> call(MoviesData moviesData) {
                        return moviesData.getMovies();
                    }
                });
                break;
            case FAVORITES:
                moviesDataObservable = Observable.zip(moviesService.getPopularMovies(), moviesService.getTopRatedMovies(), new Func2<MoviesData, MoviesData, List<Movie>>() {
                    @Override
                    public List<Movie> call(MoviesData moviesData, MoviesData moviesData2) {
                        List<Movie> combinedList = new ArrayList<>();
                        List<Movie> filteredList = new ArrayList<>();
                        List<Integer> favoritesList = movieFavoritesController.getFavoritesIdList();
                        combinedList.addAll(moviesData.getMovies());
                        combinedList.addAll(moviesData2.getMovies());
                        for (Movie movie : combinedList) {
                            if (favoritesList.contains(movie.getId())) {
                                filteredList.add(movie);
                            }
                        }
                        return filteredList;
                    }
                });
                break;
            default:
                moviesDataObservable = moviesService.getTopRatedMovies().switchMap(new Func1<MoviesData, Observable<List<Movie>>>() {
                    @Override
                    public Observable<List<Movie>> call(MoviesData moviesData) {
                        return Observable.just(moviesData.getMovies());
                    }
                });
        }
        return moviesDataObservable;
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
