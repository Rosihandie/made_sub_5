package com.rosihandie.moviecatalogue_sub_5.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rosihandie.moviecatalogue_sub_5.R;
import com.rosihandie.moviecatalogue_sub_5.activity.MoviesDetailActivity;
import com.rosihandie.moviecatalogue_sub_5.model.Movies;

import java.util.ArrayList;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder> {

    private ArrayList<Movies> movieList = new ArrayList<>();
    private final Activity activity;

    public FavMovieAdapter(Activity activity) {
        this.activity = activity;
    }

    private ArrayList<Movies> getMovieList(){
        return movieList;
    }

    public void setMovieList(ArrayList<Movies> movieListFav) {
        if (movieListFav.size() > 0)
            this.movieList.clear();
            this.movieList.addAll(movieListFav);

        notifyDataSetChanged();
    }

    public void addItem(Movies movies) {
        this.movieList.add(movies);
        notifyItemInserted(movieList.size() - 1);
    }

    public void removeItem(int position) {
        this.movieList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movieList.size());
    }


    @NonNull
    @Override
    public FavMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_favorite, parent, false);
        FavMovieViewHolder favMovieViewHolder = new FavMovieViewHolder(v);
        return favMovieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieAdapter.FavMovieViewHolder holder, int position) {
        holder.bind(movieList.get(position));

    }

    @Override
    public int getItemCount() {
        return getMovieList().size();
    }

    class FavMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tv_title;

        FavMovieViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_favorite);
            tv_title = itemView.findViewById(R.id.tv_favorite_title);

        }

        void bind(Movies movies) {
            String url_image = "https://image.tmdb.org/t/p/w185" + movies.getPhoto();
            tv_title.setText(movies.getTitle());

            Glide.with(activity)
                    .load(url_image)
                    .apply(new RequestOptions()
                            .placeholder(R.color.colorDefault))
                    .dontAnimate()
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movies movie = getMovieList().get(position);

            movie.setTitle(movie.getTitle());
            movie.setPhoto(movie.getPhoto());

            Intent moveWithObjectIntent = new Intent(activity, MoviesDetailActivity.class);
            moveWithObjectIntent.putExtra(MoviesDetailActivity.EXTRA_MOVIES, movie);
            activity.startActivity(moveWithObjectIntent);
        }
    }
}
