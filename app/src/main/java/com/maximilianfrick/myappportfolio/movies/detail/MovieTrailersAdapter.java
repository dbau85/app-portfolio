package com.maximilianfrick.myappportfolio.movies.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximilianfrick.myappportfolio.R;
import com.maximilianfrick.myappportfolio.movies.models.Trailer;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.ViewHolder> {

   private List<Trailer> trailers = Collections.emptyList();
   private MoviesDetailView.OnTrailerClickListener listener;

   MovieTrailersAdapter(MoviesDetailView.OnTrailerClickListener listener) {
      this.listener = listener;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_trailer, parent, false);
      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(ViewHolder holder, int position) {
      holder.updateView(position);
   }

   @Override
   public int getItemCount() {
      return trailers.size();
   }

   void setTrailers(List<Trailer> trailers) {
      this.trailers = trailers;
      notifyDataSetChanged();
   }

   class ViewHolder extends RecyclerView.ViewHolder {
      @BindView (R.id.txt_type)
      TextView txtType;
      @BindView (R.id.txt_trailer)
      TextView txtTrailer;

      ViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
         view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listener.onTrailerClicked(trailers.get(getAdapterPosition()));
            }
         });
      }

      void updateView(int position) {
         txtType.setText(trailers.get(position)
               .getType());
         txtTrailer.setText(trailers.get(position)
               .getName());
      }
   }
}
