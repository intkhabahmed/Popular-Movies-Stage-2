package com.intkhabahmed.popularmoviesstage2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.intkhabahmed.popularmoviesstage2.R;
import com.intkhabahmed.popularmoviesstage2.model.Cast;
import com.intkhabahmed.popularmoviesstage2.utils.AppConstants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastsAdapter extends RecyclerView.Adapter<CastsAdapter.CastViewHolder> {

    private List<Cast> mCasts;
    private Context mContext;

    public CastsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cast_item_layout, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Cast cast = mCasts.get(position);
        Glide.with(mContext).asBitmap().load(AppConstants.BASE_IMAGE_URL_185 + cast.getProfilePath()).into(holder.profilePhoto);
        holder.characterName.setText(cast.getMovieCharacter());
        holder.originalName.setText(cast.getOriginalName());
    }

    @Override
    public int getItemCount() {
        if (mCasts == null) {
            return 0;
        }
        return mCasts.size();
    }

    public void setCasts(List<Cast> mCasts) {
        this.mCasts = mCasts;
        notifyDataSetChanged();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profilePhoto;
        private TextView originalName;
        private TextView characterName;

        CastViewHolder(View itemView) {
            super(itemView);
            profilePhoto = itemView.findViewById(R.id.cast_profile_civ);
            originalName = itemView.findViewById(R.id.original_name_tv);
            characterName = itemView.findViewById(R.id.character_name_tv);
        }
    }
}
