package com.maximilianfrick.myappportfolio.movies.detail;

import com.maximilianfrick.myappportfolio.BasePresenter;
import com.maximilianfrick.myappportfolio.BaseView;
import com.maximilianfrick.myappportfolio.movies.models.Movie;

public interface MoviesDetailContract {
    interface View extends BaseView<Presenter> {
        void showMovieDetails(Movie movie);
    }

    interface Presenter extends BasePresenter {

    }
}
