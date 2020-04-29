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
import com.rosihandie.moviecatalogue_sub_5.activity.TvShowDetailActivity;
import com.rosihandie.moviecatalogue_sub_5.model.TvShow;

import java.util.ArrayList;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.FavTvViewHolder> {

    private ArrayList<TvShow> tvList = new ArrayList<>();
    private final Activity activity;

    public FavTvAdapter(Activity activity) {
        this.activity = activity;
    }

    private ArrayList<TvShow> getTvList(){
        return tvList;
    }

    public void setTvList(ArrayList<TvShow> tvList) {
        if (tvList.size() > 0)
            this.tvList.clear();
            this.tvList.addAll(tvList);

        notifyDataSetChanged();
    }

    public void addItem(TvShow tvShow) {
        this.tvList.add(tvShow);
        notifyItemInserted(tvList.size() - 1);
    }

    public void removeItem(int position) {
        this.tvList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tvList.size());
    }

    @NonNull
    @Override
    public FavTvAdapter.FavTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_favorite, parent, false);
        FavTvAdapter.FavTvViewHolder favTvViewHolder = new FavTvAdapter.FavTvViewHolder(v);
        return favTvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavTvAdapter.FavTvViewHolder holder, int position) {
        holder.bind(tvList.get(position));

    }

    @Override
    public int getItemCount() {
        return getTvList().size();
    }

    class FavTvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgPhoto;
        TextView tv_title;

        FavTvViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_favorite);
            tv_title = itemView.findViewById(R.id.tv_favorite_title);

        }

        void bind(TvShow tvShow) {
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPhoto();

            tv_title.setText(tvShow.getTitle());

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
            TvShow tvShow = getTvList().get(position);

            tvShow.setTitle(tvShow.getTitle());
            tvShow.setPhoto(tvShow.getPhoto());

            Intent moveWithObjectIntent = new Intent(activity, TvShowDetailActivity.class);
            moveWithObjectIntent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvShow);
            activity.startActivity(moveWithObjectIntent);

        }
    }
}
