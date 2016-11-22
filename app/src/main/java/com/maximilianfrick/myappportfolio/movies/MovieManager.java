package com.maximilianfrick.myappportfolio.movies;

import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.MoviesData;
import com.maximilianfrick.myappportfolio.movies.models.ReviewsData;
import com.maximilianfrick.myappportfolio.movies.models.TrailersData;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public class MovieManager {

   private MoviesService moviesService;
   private BehaviorSubject<Movie> movieDetailSubject = BehaviorSubject.create();

   public MovieManager(MoviesService moviesService) {
      this.moviesService = moviesService;
   }

   public Observable<MoviesData> getPopularMovies() {
      return moviesService.getPopularMovies();
   }

   public Observable<ReviewsData> getReviews(Integer id) {
      return moviesService.getReviews(id);
   }

   public Observable<MoviesData> getTopRatedMovies() {
      return moviesService.getTopRatedMovies();
   }

   public Observable<TrailersData> getTrailers(Integer id) {
      return moviesService.getTrailers(id);
   }

   public void setMovieDetail(Movie movie) {
      movieDetailSubject.onNext(movie);
   }

   public BehaviorSubject<Movie> getMovieDetail() {
      return movieDetailSubject;
   }
}
