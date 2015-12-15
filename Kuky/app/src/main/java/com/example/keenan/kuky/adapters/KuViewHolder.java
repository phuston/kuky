package com.example.keenan.kuky.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.keenan.kuky.R;

public class KuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected CardView vCardView;
    protected TextView vKuContent1;
    protected TextView vKuContent2;
    protected TextView vKuContent3;
    protected TextView vKuKarma;
    protected ImageButton vUpvote, vDownvote, vFavorite;
    protected boolean vUpvotePressed, vDownvotePressed;

    private ClickListener vClickListener;

    public KuViewHolder(View v) {
        super(v);
        vCardView = (CardView) v.findViewById(R.id.ku_card_view);
        vKuContent1 = (TextView) v.findViewById(R.id.ku_content1);
        vKuContent2 = (TextView) v.findViewById(R.id.ku_content2);
        vKuContent3 = (TextView) v.findViewById(R.id.ku_content3);
        vKuKarma = (TextView) v.findViewById(R.id.ku_karma);
        vUpvote = (ImageButton) v.findViewById(R.id.upvoteButton);
        vDownvote = (ImageButton) v.findViewById(R.id.downvoteButton);
        vFavorite = (ImageButton) v.findViewById(R.id.favoriteButton);
        vUpvotePressed = false;
        vDownvotePressed = false;

        v.setOnClickListener(this);
    }

    public interface ClickListener {
        public void onClick(View v, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.vClickListener = clickListener;
    }

    @Override
    public void onClick(View v){
        vClickListener.onClick(v, getPosition());
    }

}
