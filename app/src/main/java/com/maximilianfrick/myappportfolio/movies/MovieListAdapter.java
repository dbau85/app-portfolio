package com.maximilianfrick.myappportfolio.movies;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maximilianfrick.myappportfolio.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private List<Uri> movieImages;
    private final Picasso picasso;

    MovieListAdapter(Context context, List<Uri> movieImages) {
        this.movieImages = movieImages;
        picasso = Picasso.with(context);
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
        return movieImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView poster;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void updateView(int position) {
            picasso.load(movieImages.get(position)).config(Bitmap.Config.RGB_565).into(poster);
        }
    }
}
