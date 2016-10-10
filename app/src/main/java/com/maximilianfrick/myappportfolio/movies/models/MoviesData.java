package com.maximilianfrick.myappportfolio.movies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MoviesData {

    @SerializedName("results")
    @Expose
    private List<Movie> movies = new ArrayList<Movie>();

    public List<Movie> getMovies() {
        return movies;
    }
}
