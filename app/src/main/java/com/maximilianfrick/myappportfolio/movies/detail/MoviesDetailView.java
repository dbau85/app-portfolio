package com.maximilianfrick.myappportfolio.movies.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.maximilianfrick.myappportfolio.movies.models.Review;
import com.maximilianfrick.myappportfolio.movies.models.Trailer;
import com.maximilianfrick.myappportfolio.utils.NonScrollExpandableListView;
import com.maximilianfrick.myappportfolio.utils.SimpleDividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesDetailView extends FrameLayout implements MoviesDetailContract.View {
    public static final String DATE_PATTERN_MOVIE_DB = "yyyy-MM-dd";

    @BindView(R.id.img_backdrop)
    ImageView imgBackDrop;
    @BindView(R.id.img_movie_poster)
    ImageView imgPoster;
    @BindView(R.id.txt_year)
    TextView txtYear;
    @BindView(R.id.txt_plot)
    TextView txtPlot;
    @BindView(R.id.txt_rating)
    TextView txtRating;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.expandablelistview)
    NonScrollExpandableListView expandableListView;

    MoviesDetailContract.Presenter presenter;
    MovieTrailersAdapter trailersAdapter;
    ReviewsExpListViewAdapter reviewsAdapter;

    public MoviesDetailView(Context context) {
        super(context);
        init();
    }

    public MoviesDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoviesDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.content_movies_detail, this);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        trailersAdapter = new MovieTrailersAdapter(listener);
        reviewsAdapter = new ReviewsExpListViewAdapter();
        recyclerView.setAdapter(trailersAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        // this improves the scrolling behaviour of the NestedScrollView
        recyclerView.setNestedScrollingEnabled(false);
        expandableListView.setAdapter(reviewsAdapter);
    }

    @Override
    public void showMovieDetails(Movie movie) {
        if (movie == null) {
            return;
        }
        Picasso picasso = Picasso.with(getContext());
        String backDropPath = getContext().getString(R.string.base_url_images_backdrop) + movie.getBackdropPath();
        String posterPath = getContext().getString(R.string.base_url_images_poster) + movie.getPosterPath();
        DateFormat from = new SimpleDateFormat(DATE_PATTERN_MOVIE_DB, Locale.getDefault());
        DateFormat to = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        try {
            Date date = from.parse(movie.getReleaseDate());
            txtYear.setText(to.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        picasso.load(backDropPath).into(imgBackDrop);
        picasso.load(posterPath).into(imgPoster);
        txtRating.setText(getContext().getString(R.string.vote_average, String.valueOf(movie.getVoteAverage())));
        txtPlot.setText(movie.getOverview());
    }

    @Override
    public void showTrailerList(List<Trailer> trailers) {
        trailersAdapter.setTrailers(trailers);
    }

    @Override
    public void showErrorNoInternet() {
        Snackbar.make(this, getContext().getString(R.string.err_msg_no_internet), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showReviews(List<Review> reviews) {
        reviewsAdapter.setReviews(reviews);
    }

    @Override
    public void setPresenter(@NonNull MoviesDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    OnTrailerClickListener listener = new OnTrailerClickListener() {
        @Override
        public void onTrailerClicked(Trailer trailer) {
            String url = "https://www.youtube.com/watch?v=" + trailer.getKey();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            getContext().startActivity(i);
        }
    };

    public interface OnTrailerClickListener {
        void onTrailerClicked(Trailer trailer);
    }
}
