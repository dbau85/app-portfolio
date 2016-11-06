package com.maximilianfrick.myappportfolio.movies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.detail.MoviesDetailActivity;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.utils.ActivityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

public class MoviesView extends FrameLayout implements MoviesContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private MoviesListAdapter adapter;
    private MoviesContract.Presenter presenter;

    public MoviesView(Context context) {
        super(context);
        init();
    }

    public MoviesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoviesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.content_movies_list, this);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),
                getContext().getResources().getInteger(R.integer.movie_poster_row_count)));
        adapter = new MoviesListAdapter(getContext(), listener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(@NonNull MoviesContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        adapter.setItems(movies);
    }

    @Override
    public void showFilteringOptions() {
        PopupMenu popupMenu = new PopupMenu(getContext(), getRootView().findViewById(R.id.menu_filter));
        popupMenu.getMenuInflater().inflate(R.menu.filter_movies, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.filter_popular_movies:
                        presenter.setFilterType(MoviesFilterType.POPULAR_MOVIES);
                        break;
                    case R.id.filter_top_rated_movies:
                        presenter.setFilterType(MoviesFilterType.TOP_RATED_MOVIES);
                        break;
                    default:
                        return false;
                }
                presenter.loadMovies();
                return true;
            }
        });
    }

    @Override
    public void showErrorNoInternet() {
        Snackbar.make(this, getContext().getString(R.string.err_msg_no_internet), Snackbar.LENGTH_LONG).show();
    }

    OnPosterClickListener listener = new OnPosterClickListener() {
        @Override
        public void onPosterClicked(Movie movie) {
            Intent intent = MoviesDetailActivity.newIntent(getContext(), movie);
            ActivityUtils.getActivity(MoviesView.this).startActivity(intent);
        }
    };

    public interface OnPosterClickListener {
        void onPosterClicked(Movie item);
    }
}
