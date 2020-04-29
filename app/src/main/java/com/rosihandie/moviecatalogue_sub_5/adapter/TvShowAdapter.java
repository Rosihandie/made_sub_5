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
import com.rosihandie.moviecatalogue_sub_5.activity.TvShowDetailActivity;
import com.rosihandie.moviecatalogue_sub_5.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private ArrayList<TvShow> listTvshow;
    private final Context context;

    public TvShowAdapter(Context context) {
        this.context = context;
        listTvshow = new ArrayList<>();
    }

    private ArrayList<TvShow> getListTvshow() {
        return listTvshow;
    }

    public void setListTvshow(ArrayList<TvShow> listTvshow) {
        this.listTvshow = listTvshow;
    }

    @NonNull
    @Override
    public TvShowAdapter.TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_tv, viewGroup, false);
        TvShowAdapter.TvShowViewHolder tvshowHolder = new TvShowAdapter.TvShowViewHolder(v);
        return tvshowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.TvShowViewHolder holder, int position) {
        holder.bind(getListTvshow().get(position));
    }

    @Override
    public int getItemCount() {
        return getListTvshow().size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tv_title;
        TextView tv_release;
        TextView tv_rating;
        TextView tv_description;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_item_name_tv);
            tv_release = itemView.findViewById(R.id.tv_item_date_tv);
            tv_rating = itemView.findViewById(R.id.tv_item_rating_tv);
            tv_description = itemView.findViewById(R.id.tv_item_description_tv);
            imgPhoto = itemView.findViewById(R.id.img_item_tv);

            itemView.setOnClickListener(this);
        }

        void bind(TvShow tvShow) {
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPhoto();

            tv_title.setText(tvShow.getTitle());
            tv_release.setText(tvShow.getRelease());
            tv_rating.setText(tvShow.getRating());
            tv_description.setText(tvShow.getDescription());

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
                TvShow tvShow = getListTvshow().get(position);

                Intent moveWithObjectIntent = new Intent(context, TvShowDetailActivity.class);
                moveWithObjectIntent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvShow);
                context.startActivity(moveWithObjectIntent);
            }
        }
    }
}
