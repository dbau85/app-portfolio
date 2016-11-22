package com.maximilianfrick.myappportfolio.movies.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {
    private List<Movie> movies = Collections.emptyList();
    private final Picasso picasso;
    private String imageBaseUrl;
    private final MoviesView.OnPosterClickListener listener;

    MoviesListAdapter(Context context, MoviesView.OnPosterClickListener listener) {
        imageBaseUrl = context.getString(R.string.base_url_images_poster);
        picasso = Picasso.with(context);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateView(position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    void setItems(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_movie_poster)
        ImageView poster;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.img_movie_poster)
        void onClick(){
            listener.onPosterClicked(movies.get(getAdapterPosition()));
        }

        void updateView(int position) {
            String finalPosterPath = imageBaseUrl + movies.get(position).getPosterPath();
            picasso.load(Uri.parse(finalPosterPath)).config(Bitmap.Config.RGB_565).into(poster);
        }
    }
}
