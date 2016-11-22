package com.maximilianfrick.myappportfolio.movies.detail;

import com.maximilianfrick.myappportfolio.BaseView;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.Review;
import com.maximilianfrick.myappportfolio.movies.models.Trailer;

import java.util.List;

interface MoviesDetailContract {
   interface View extends BaseView<MoviesDetailPresenter> {
      void showMovieDetails(Movie movie);

      void showTrailerList(List<Trailer> trailers);

      void showErrorNoInternet();

      void showReviews(List<Review> reviews);
   }
}
