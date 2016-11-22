package com.maximilianfrick.myappportfolio.movies.list;

import com.maximilianfrick.myappportfolio.BaseView;
import com.maximilianfrick.myappportfolio.Presenter;
import com.maximilianfrick.myappportfolio.movies.models.Movie;

import java.util.List;

public interface MoviesContract {
    interface View extends BaseView<MoviesPresenter> {
        void showMovies(List<Movie> movies);

        void showFilteringOptions();

        void showErrorNoInternet();
    }
}
