package com.maximilianfrick.myappportfolio.movies.detail;

import com.maximilianfrick.myappportfolio.BasePresenter;
import com.maximilianfrick.myappportfolio.BaseView;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.Review;
import com.maximilianfrick.myappportfolio.movies.models.Trailer;

import java.util.List;

public interface MoviesDetailContract {
    interface View extends BaseView<Presenter> {
        void showMovieDetails(Movie movie);

        void showTrailerList(List<Trailer> trailers);

        void showErrorNoInternet();

        void showReviews(List<Review> reviews);

    }

    interface Presenter extends BasePresenter {

        void addToFavorites(boolean isFavorite);

        void loadMovie(Movie movie);
    }
}
