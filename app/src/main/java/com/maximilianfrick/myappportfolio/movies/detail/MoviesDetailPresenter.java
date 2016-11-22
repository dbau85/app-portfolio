package com.maximilianfrick.myappportfolio.movies.detail;

import android.content.Context;

import com.maximilianfrick.myappportfolio.Presenter;
import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.MovieManager;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.ReviewsData;
import com.maximilianfrick.myappportfolio.movies.models.TrailersData;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MoviesDetailPresenter extends Presenter<MoviesDetailContract.View> {

   @Inject
   MovieFavoritesController movieFavoritesController;
   @Inject
   MovieManager movieManager;
   private Movie movie;
   private CompositeSubscription subscriptions = new CompositeSubscription();

   public MoviesDetailPresenter() {
      Injector.getAppComponent()
            .inject(this);
   }

   void addToFavorites(boolean isFavorite) {
      if (isFavorite) {
         movieFavoritesController.addMovieToFavorites(movie);
      } else {
         movieFavoritesController.deleteMovieFromFavorites(movie);
      }
   }

   @Override
   protected void bindView(MoviesDetailContract.View view, Context context) {
      super.bindView(view, context);
      loadDetail();
   }

   @Override
   protected void unbindView() {
      super.unbindView();
      if (subscriptions != null && !subscriptions.isUnsubscribed()) {
         subscriptions.unsubscribe();
      }
   }

   private void handleFavoriteStatus(Movie movie) {
      this.movie.setFavorite(movieFavoritesController.isFavorite(movie));
   }

   private void loadReviews() {
      subscriptions.add(movieManager.getReviews(movie.getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<ReviewsData>() {
               @Override
               public void call(ReviewsData reviewsData) {
                  getView().showReviews(reviewsData.getReviews());
               }
            }, new Action1<Throwable>() {
               @Override
               public void call(Throwable throwable) {
                  getView().showErrorNoInternet();
               }
            }));
   }

   private void loadTrailers() {
      subscriptions.add(movieManager.getTrailers(movie.getId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<TrailersData>() {
               @Override
               public void call(TrailersData trailersData) {
                  getView().showTrailerList(trailersData.getTrailers());
               }
            }, new Action1<Throwable>() {
               @Override
               public void call(Throwable throwable) {
                  getView().showErrorNoInternet();
               }
            }));
   }

   private void loadDetail() {
      subscriptions.add(movieManager.getMovieDetail()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Movie>() {
               @Override
               public void call(Movie movie) {
                  MoviesDetailPresenter.this.movie = movie;
                  start();
               }
            }, new Action1<Throwable>() {
               @Override
               public void call(Throwable throwable) {
                  getView().showErrorNoInternet();
               }
            }));
   }

   private void start() {
      if (movie == null) {
         return;
      }
      handleFavoriteStatus(movie);
      getView().showMovieDetails(movie);
      loadTrailers();
      loadReviews();
   }
}
