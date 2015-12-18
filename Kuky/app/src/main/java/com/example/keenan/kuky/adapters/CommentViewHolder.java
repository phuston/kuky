package com.example.keenan.kuky.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keenan.kuky.R;

/**
 * aServes as an interface between the Comment view and the CommentCardAdapter
 */
public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected CardView vCardView;
    protected TextView vCommentContent;
    protected TextView vCommentKarma;
    protected ImageView vUpvote, vDownvote, vFavorite;

    private ClickListener vClickListener;

    public CommentViewHolder(View v) {
        super(v);
        vCardView = (CardView) v.findViewById(R.id.comment_card_view);
        vCommentContent = (TextView) v.findViewById(R.id.comment_content);
        vCommentKarma = (TextView) v.findViewById(R.id.comment_karma);
        vUpvote = (ImageButton) v.findViewById(R.id.upvoteButton);
        vDownvote = (ImageButton) v.findViewById(R.id.downvoteButton);
        vFavorite = (ImageButton) v.findViewById(R.id.favoriteButton);
    }

    public interface ClickListener {
        public void onClick(View v, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.vClickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        vClickListener.onClick(v, getPosition());
    }
}
