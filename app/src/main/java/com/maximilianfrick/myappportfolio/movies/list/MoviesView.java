package com.maximilianfrick.myappportfolio.movies.list;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.maximilianfrick.myappportfolio.PresenterView;
import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.models.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesView extends PresenterView<MoviesContract.View, MoviesPresenter>
      implements MoviesContract.View {

   public interface OnPosterClickListener {
      void onPosterClicked(Movie item);
   }

   @BindView (R.id.recyclerview)
   RecyclerView recyclerView;
   private MoviesListAdapter adapter;

   public MoviesView(Context context, AttributeSet attrs) {
      super(context, attrs, MoviesPresenter.class);
   }

   public MoviesView(Context context) {
      super(context, MoviesPresenter.class);
   }

   public MoviesView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr, MoviesPresenter.class);
   }

   @Override
   public void showErrorNoInternet() {
      Snackbar.make(this, getContext().getString(R.string.err_msg_no_internet),
            Snackbar.LENGTH_LONG)
            .show();
   }

   @Override
   public void showFilteringOptions() {
      PopupMenu popupMenu =
            new PopupMenu(getContext(), getRootView().findViewById(R.id.menu_filter));
      popupMenu.getMenuInflater()
            .inflate(R.menu.filter_movies, popupMenu.getMenu());
      popupMenu.show();
      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
         @Override
         public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
               case R.id.filter_popular_movies:
                  getPresenter().setFilterType(MoviesFilterType.POPULAR_MOVIES);
                  break;
               case R.id.filter_top_rated_movies:
                  getPresenter().setFilterType(MoviesFilterType.TOP_RATED_MOVIES);
                  break;
               case R.id.filter_favorites:
                  getPresenter().setFilterType(MoviesFilterType.FAVORITES);
                  break;
               default:
                  return false;
            }
            return true;
         }
      });
   }

   @Override
   public void showMovies(List<Movie> movies) {
      adapter.setItems(movies);
   }

   @Override
   protected void initialize(Context context) {
      super.initialize(context);
      inflate(getContext(), R.layout.content_movies_list, this);
      ButterKnife.bind(this);
      recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getContext().getResources()
            .getInteger(R.integer.movie_poster_row_count)));
      adapter = new MoviesListAdapter(getContext(), new OnPosterClickListener() {
         @Override
         public void onPosterClicked(Movie movie) {
            getPresenter().onPosterClick(movie);
         }
      });
      recyclerView.setAdapter(adapter);
   }
}
