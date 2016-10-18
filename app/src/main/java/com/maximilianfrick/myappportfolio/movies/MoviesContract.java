package com.maximilianfrick.myappportfolio.movies;

import com.maximilianfrick.myappportfolio.BasePresenter;
import com.maximilianfrick.myappportfolio.BaseView;

import java.util.ArrayList;

public interface MoviesContract {
    interface View extends BaseView<Presenter> {
        void showMovies(ArrayList<String> moviePosterPaths);
    }

    interface Presenter extends BasePresenter {
        void loadMovies(MoviesFilterType filterType);
    }
}
