package com.maximilianfrick.myappportfolio.movies;

import com.maximilianfrick.myappportfolio.BasePresenter;
import com.maximilianfrick.myappportfolio.BaseView;

import java.util.List;

public interface MoviesContract {
    interface View extends BaseView<Presenter> {
        void showMovies(List<String> moviePosterPaths);

        void showFilteringOptions();
    }

    interface Presenter extends BasePresenter {
        void loadMovies();

        MoviesFilterType getFilterType();

        void setFilterType(MoviesFilterType filterType);
    }
}
