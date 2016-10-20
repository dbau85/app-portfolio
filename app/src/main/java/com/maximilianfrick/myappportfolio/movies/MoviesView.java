package com.maximilianfrick.myappportfolio.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.maximilianfrick.myappportfolio.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

public class MoviesView extends FrameLayout implements MoviesContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private MovieListAdapter adapter;

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
        inflate(getContext(), R.layout.movies_view_content, this);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new MovieListAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(@NonNull MoviesContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showMovies(List<String> moviePosterPaths) {
        adapter.setItems(moviePosterPaths);
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
}
