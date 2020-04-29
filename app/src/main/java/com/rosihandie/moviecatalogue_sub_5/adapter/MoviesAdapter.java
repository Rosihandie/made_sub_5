package com.rosihandie.moviecatalogue_sub_5.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rosihandie.moviecatalogue_sub_5.R;
import com.rosihandie.moviecatalogue_sub_5.activity.MoviesDetailActivity;
import com.rosihandie.moviecatalogue_sub_5.model.Movies;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private ArrayList<Movies> listMovies;
    private final Context context;

    public MoviesAdapter(Context context) {
        this.context = context;
        listMovies = new ArrayList<>();
    }

    private ArrayList<Movies> getListMovies() {
        return listMovies;
    }

    public void setListMovies(ArrayList<Movies> listMovies) {
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment, viewGroup, false);
        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, final int position) {
        holder.bind(getListMovies().get(position));
    }

    @Override
    public int getItemCount() {
        return getListMovies().size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imgPhoto;
        final TextView tv_title;
        final TextView tv_release;
        final TextView tv_rating;
        final TextView tv_description;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_item_name);
            tv_release = itemView.findViewById(R.id.tv_item_date);
            tv_rating = itemView.findViewById(R.id.tv_item_rating);
            tv_description = itemView.findViewById(R.id.tv_item_description);
            imgPhoto = itemView.findViewById(R.id.img_item);

            itemView.setOnClickListener(this);
        }

        void bind(Movies movies) {
            String url_image = "https://image.tmdb.org/t/p/w185" + movies.getPhoto();

            tv_title.setText(movies.getTitle());
            tv_release.setText(movies.getRelease());
            tv_rating.setText(movies.getRating());
            tv_description.setText(movies.getDescription());

            Glide.with(context)
                    .load(url_image)
                    .apply(new RequestOptions()
                            .placeholder(R.color.colorDefault)
                            .transform(new RoundedCorners(16)))
                    .dontAnimate()
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Movies movie = getListMovies().get(position);

                Intent moveWithObjectIntent = new Intent(context, MoviesDetailActivity.class);
                moveWithObjectIntent.putExtra(MoviesDetailActivity.EXTRA_MOVIES, movie);
                context.startActivity(moveWithObjectIntent);
            }
        }
    }
}
