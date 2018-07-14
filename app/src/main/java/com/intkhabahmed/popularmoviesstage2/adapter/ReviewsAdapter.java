package com.intkhabahmed.popularmoviesstage2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intkhabahmed.popularmoviesstage2.R;
import com.intkhabahmed.popularmoviesstage2.model.Review;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private List<Review> mReviews;
    private Context mContext;

    public ReviewsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_item_layout, parent, false);
        return new ReviewsAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.authorPic.setText(review.getAuthor().substring(0, 1));
        holder.reviewContent.setText(review.getReviewMessage());
        holder.authorName.setText(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (mReviews == null) {
            return 0;
        }
        return mReviews.size();
    }

    public void setReviews(List<Review> mReviews) {
        this.mReviews = mReviews;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView authorPic;
        private TextView authorName;
        private TextView reviewContent;

        ReviewViewHolder(View itemView) {
            super(itemView);
            authorPic = itemView.findViewById(R.id.author_pic_tv);
            authorName = itemView.findViewById(R.id.author_name_tv);
            reviewContent = itemView.findViewById(R.id.review_content_tv);
        }
    }
}
