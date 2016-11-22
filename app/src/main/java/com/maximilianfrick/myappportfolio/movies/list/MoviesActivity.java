package com.maximilianfrick.myappportfolio.movies.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.core.dagger.Injector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends AppCompatActivity {
   @BindView (R.id.view_movies)
   MoviesContract.View moviesView;

   public static Intent newIntent(Activity activity) {
      return new Intent(activity, MoviesActivity.class);
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_movies);
      ButterKnife.bind(this);
      Injector.getAppComponent()
            .inject(this);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.movies_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.menu_filter:
            moviesView.showFilteringOptions();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }
}
