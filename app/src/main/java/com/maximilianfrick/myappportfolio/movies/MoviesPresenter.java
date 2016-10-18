package com.maximilianfrick.myappportfolio.movies;

import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.MoviesData;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MoviesPresenter implements MoviesContract.Presenter {

    @Inject
    MoviesService moviesService;
    private ArrayList<String> moviePosterPaths;
    private final MoviesContract.View moviesView;

    @Override
    public void start() {
        Injector.getAppComponent().inject(this);
        loadMovies(null);
    }

    public MoviesPresenter(MoviesContract.View view) {
        this.moviesView = view;
    }

    @Override
    public void loadMovies(MoviesFilterType filterType) {
        moviePosterPaths = new ArrayList<>();
        Observable<MoviesData> observable = moviesService.getPopularMovies();
        observable.observeOn(AndroidSchedulers.mainThread())
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
                        // HANDLE ERROR
                    }
                });

    }
}
