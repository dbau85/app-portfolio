package com.maximilianfrick.myappportfolio.movies;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.maximilianfrick.myappportfolio.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesView extends FrameLayout implements MoviesContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private MovieListAdapter adapter;


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
    public void setPresenter(MoviesContract.Presenter presenter) {

    }

    @Override
    public void showMovies(ArrayList<String> moviePosterPaths) {
        adapter.setItems(moviePosterPaths);
    }
}
