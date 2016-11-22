package com.maximilianfrick.myappportfolio.movies.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.core.dagger.Injector;
import com.maximilianfrick.myappportfolio.movies.MovieManager;
import com.maximilianfrick.myappportfolio.movies.models.Movie;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesDetailActivity extends AppCompatActivity {

   public static final String KEY_EXTRA_MOVIE = "KEY_EXTRA_MOVIE";
   @BindView (R.id.collapsing_toolbar)
   CollapsingToolbarLayout collapsingToolbarLayout;
   @Inject
   MovieManager movieManager;

   public static Intent newIntent(Context context, Movie movie) {
      Intent intent = new Intent(context, MoviesDetailActivity.class);
      intent.putExtra(KEY_EXTRA_MOVIE, Parcels.wrap(movie));
      return intent;
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_movies_detail);
      Injector.getAppComponent()
            .inject(this);
      ButterKnife.bind(this);

      Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(KEY_EXTRA_MOVIE));
      initToolbar(movie);
      movieManager.setMovieDetail(movie);
   }

   private void initToolbar(Movie movie) {
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
         actionBar.setDisplayHomeAsUpEnabled(true);
      }

      String itemTitle = movie.getOriginalTitle();
      collapsingToolbarLayout.setTitle(itemTitle);
   }
}
