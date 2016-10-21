package com.maximilianfrick.myappportfolio.movies.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesDetailView extends FrameLayout implements MoviesDetailContract.View {
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

    MoviesDetailContract.Presenter presenter;

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
    }

    @Override
    public void showMovieDetails(Movie movie) {
        Picasso picasso = Picasso.with(getContext());
        String backDropPath = getContext().getString(R.string.base_url_images_backdrop) + movie.getBackdropPath();
        String posterPath = getContext().getString(R.string.base_url_images_poster) + movie.getPosterPath();
        DateFormat from = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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
    public void setPresenter(MoviesDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
