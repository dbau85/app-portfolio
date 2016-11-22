package com.maximilianfrick.myappportfolio.movies.detail;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maximilianfrick.myappportfolio.movies.models.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieFavoritesController {

   private static final String KEY_FAVORITES_LIST = "KEY_FAVORITES_LIST";
   private SharedPreferences sharedPreferences;

   public MovieFavoritesController(SharedPreferences sharedPreferences) {
      this.sharedPreferences = sharedPreferences;
   }

   private static Type typeToken = new TypeToken<List<Integer>>() {
   }.getType();

   public List<Integer> getFavoritesIdList() {
      String json = sharedPreferences.getString(KEY_FAVORITES_LIST, null);
      if (json == null) {
         return new ArrayList<>();
      }
      return new Gson().fromJson(json, typeToken);
   }

   void addMovieToFavorites(Movie movie) {
      List<Integer> favoriteMovieIdList = getFavoritesIdList();
      if (favoriteMovieIdList.contains(movie.getId())) {
         return;
      }
      favoriteMovieIdList.add(movie.getId());
      sharedPreferences.edit()
            .putString(KEY_FAVORITES_LIST, new Gson().toJson(favoriteMovieIdList))
            .apply();
   }

   void deleteMovieFromFavorites(Movie movie) {
      List<Integer> favoriteMovieIdList = getFavoritesIdList();
      if (!favoriteMovieIdList.contains(movie.getId())) {
         return;
      }
      favoriteMovieIdList.remove(movie.getId());
      sharedPreferences.edit()
            .putString(KEY_FAVORITES_LIST, new Gson().toJson(favoriteMovieIdList))
            .apply();
   }

   boolean isFavorite(Movie movie) {
      return getFavoritesIdList().contains(movie.getId());
   }
}
