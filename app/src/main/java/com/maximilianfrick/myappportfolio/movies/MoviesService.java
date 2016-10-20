package com.maximilianfrick.myappportfolio.movies;

import com.maximilianfrick.myappportfolio.movies.models.MoviesData;

import retrofit2.http.GET;
import rx.Observable;

public interface MoviesService {

    @GET("popular")
    Observable<MoviesData> getPopularMovies();

    @GET("top_rated")
    Observable<MoviesData> getTopRatedMovies();
}
