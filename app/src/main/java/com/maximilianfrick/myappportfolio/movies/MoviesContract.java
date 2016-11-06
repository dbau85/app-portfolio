package com.maximilianfrick.myappportfolio.movies;

import com.maximilianfrick.myappportfolio.BasePresenter;
import com.maximilianfrick.myappportfolio.BaseView;
import com.maximilianfrick.myappportfolio.movies.models.Movie;

import java.util.List;

public interface MoviesContract {
    interface View extends BaseView<Presenter> {
        void showMovies(List<Movie> movies);

        void showFilteringOptions();

        void showErrorNoInternet();
    }

    interface Presenter extends BasePresenter {
        void loadMovies();

        MoviesFilterType getFilterType();

        void setFilterType(MoviesFilterType filterType);
    }
}
