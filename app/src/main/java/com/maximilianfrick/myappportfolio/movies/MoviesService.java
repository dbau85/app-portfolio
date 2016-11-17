package com.maximilianfrick.myappportfolio.movies;

import com.maximilianfrick.myappportfolio.movies.models.MoviesData;
import com.maximilianfrick.myappportfolio.movies.models.ReviewsData;
import com.maximilianfrick.myappportfolio.movies.models.TrailersData;

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

    @GET("{id}/reviews")
    Observable<ReviewsData> getReviews(@Path("id") int movieId);
}
