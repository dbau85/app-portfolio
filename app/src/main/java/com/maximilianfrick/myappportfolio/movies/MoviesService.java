package com.maximilianfrick.myappportfolio.movies;

import com.maximilianfrick.myappportfolio.movies.models.MoviesData;
import com.maximilianfrick.myappportfolio.movies.models.Review;
import com.maximilianfrick.myappportfolio.movies.models.TrailersData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MoviesService {

    @GET("popular")
    Observable<MoviesData> getPopularMovies();

    @GET("top_rated")
    Observable<MoviesData> getTopRatedMovies();

    @GET("{id}/videos")
    Observable<TrailersData> getTrailers(@Path("id") int movieId);

    @GET("/movie/{id}/reviews")
    Observable<List<Review>> getReviews(@Path("id") int movieId);
}
