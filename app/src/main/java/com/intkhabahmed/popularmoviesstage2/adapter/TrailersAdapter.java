package com.intkhabahmed.popularmoviesstage2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.intkhabahmed.popularmoviesstage2.R;
import com.intkhabahmed.popularmoviesstage2.model.Trailer;
import com.intkhabahmed.popularmoviesstage2.utils.AppConstants;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private List<Trailer> mVideoKeys;
    private Context mContext;

    public TrailersAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.trailer_item_layout, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Glide.with(mContext).asBitmap().load(AppConstants.YOUTUBE_TUMBNAIL_URL +
                mVideoKeys.get(position).getVideoKey() + AppConstants.YOUTUBE_IMAGE_EXT)
                .into(holder.trailerPoster);
    }

    @Override
    public int getItemCount() {
        if (mVideoKeys == null) {
            return 0;
        }
        return mVideoKeys.size();
    }

    public void setTrailers(List<Trailer> videoKeys) {
        this.mVideoKeys = videoKeys;
        notifyDataSetChanged();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        private ImageView trailerPoster;

        TrailerViewHolder(View itemView) {
            super(itemView);
            trailerPoster = itemView.findViewById(R.id.trailer_poster_iv);
        }
    }
}
