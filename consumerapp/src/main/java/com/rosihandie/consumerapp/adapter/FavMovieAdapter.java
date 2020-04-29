package com.rosihandie.consumerapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rosihandie.consumerapp.R;
import com.rosihandie.consumerapp.model.Movies;
import com.squareup.picasso.Picasso;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder> {

    private Cursor movie_cursor;

    public FavMovieAdapter(Context context) {
        Context mContext = context;
    }

    public void setMovieList(Cursor movieList) {
        this.movie_cursor = movieList;
    }

    @NonNull
    @Override
    public FavMovieAdapter.FavMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_favorite, parent, false);
        FavMovieViewHolder favMovieViewHolder = new FavMovieViewHolder(v);
        return favMovieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieViewHolder holder, int position) {
        final Movies result = getItem(position);
        holder.favTitle.setText(result.getTitle());
        String poster = result.getPhoto();
        Picasso.get()
                .load(poster)
                .placeholder(R.color.colorDefault)
                .into(holder.favImg);
    }

    private Movies getItem(int position) {
        if (!movie_cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movies(movie_cursor);
    }

    @Override
    public int getItemCount() {
        if (movie_cursor == null) return 0;
        return movie_cursor.getCount();
    }

    class FavMovieViewHolder extends RecyclerView.ViewHolder {
        TextView favTitle;
        ImageView favImg;

        FavMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            favTitle = itemView.findViewById(R.id.tv_favorite_title);
            favImg = itemView.findViewById(R.id.img_item_favorite);
        }
    }

}
