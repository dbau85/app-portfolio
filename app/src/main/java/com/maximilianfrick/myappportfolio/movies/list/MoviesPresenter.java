package com.maximilianfrick.myappportfolio.movies.list;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.maximilianfrick.myappportfolio.Presenter;
import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.MovieManager;
import com.maximilianfrick.myappportfolio.movies.detail.MovieFavoritesController;
import com.maximilianfrick.myappportfolio.movies.detail.MoviesDetailActivity;
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

public class MoviesPresenter extends Presenter<MoviesContract.View> {

   @Inject
   MovieFavoritesController movieFavoritesController;
   @Inject
   MovieManager movieManager;
   private MoviesFilterType filterType = MoviesFilterType.POPULAR_MOVIES;
   private Subscription subscription;

   public MoviesPresenter() {
      Injector.getAppComponent()
            .inject(this);
   }

   void setFilterType(MoviesFilterType filterType) {
      this.filterType = filterType;
   }

   @Override
   protected void bindView(MoviesContract.View view, Context context) {
      super.bindView(view, context);
      start();
   }

   @Override
   protected void unbindView() {
      super.unbindView();
      if (subscription != null && !subscription.isUnsubscribed()) {
         subscription.unsubscribe();
      }
   }

   void onPosterClick(Movie movie) {
      if (movieManager.getMovieDetail()
            .hasObservers()) {
         movieManager.setMovieDetail(movie);
      } else {
         Intent intent = MoviesDetailActivity.newIntent(getContext(), movie);
         ActivityCompat.startActivity(getContext(), intent, null);
      }
   }

   private Observable<List<Movie>> getMoviesDataObservable() {
      Observable<List<Movie>> moviesDataObservable;
      switch (filterType) {
         case POPULAR_MOVIES:
            moviesDataObservable = movieManager.getPopularMovies()
                  .map(new Func1<MoviesData, List<Movie>>() {
                     @Override
                     public List<Movie> call(MoviesData moviesData) {
                        return moviesData.getMovies();
                     }
                  });
            break;
         case TOP_RATED_MOVIES:
            moviesDataObservable = movieManager.getTopRatedMovies()
                  .map(new Func1<MoviesData, List<Movie>>() {
                     @Override
                     public List<Movie> call(MoviesData moviesData) {
                        return moviesData.getMovies();
                     }
                  });
            break;
         case FAVORITES:
            moviesDataObservable =
                  Observable.zip(movieManager.getPopularMovies(), movieManager.getTopRatedMovies(),
                        new Func2<MoviesData, MoviesData, List<Movie>>() {
                           @Override
                           public List<Movie> call(MoviesData moviesData, MoviesData moviesData2) {
                              List<Movie> combinedList = new ArrayList<>();
                              List<Movie> filteredList = new ArrayList<>();
                              List<Integer> favoritesList =
                                    movieFavoritesController.getFavoritesIdList();
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
            moviesDataObservable = movieManager.getTopRatedMovies()
                  .switchMap(new Func1<MoviesData, Observable<List<Movie>>>() {
                     @Override
                     public Observable<List<Movie>> call(MoviesData moviesData) {
                        return Observable.just(moviesData.getMovies());
                     }
                  });
      }
      return moviesDataObservable;
   }

   private void loadMovies() {
      Observable<List<Movie>> moviesDataObservable = getMoviesDataObservable();
      subscription = moviesDataObservable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Movie>>() {
               @Override
               public void call(List<Movie> movieList) {
                  getView().showMovies(movieList);
                  if (movieList.size() > 0) {
                     movieManager.setMovieDetail(movieList.get(0));
                  }
               }
            }, new Action1<Throwable>() {
               @Override
               public void call(Throwable throwable) {
                  getView().showErrorNoInternet();
                  Log.d(getClass().getSimpleName(), "loadMovies: ", throwable);
               }
            });
   }

   private void start() {
      loadMovies();
   }
}
