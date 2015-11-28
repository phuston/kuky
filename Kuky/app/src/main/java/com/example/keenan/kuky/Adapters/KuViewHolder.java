package com.example.keenan.kuky.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.keenan.kuky.R;

public class KuViewHolder extends RecyclerView.ViewHolder {

    protected CardView vCardView;
    protected TextView vKuContent;

    public KuViewHolder(View v) {
        super(v);
        vCardView = (CardView) v.findViewById(R.id.ku_card_view);
        vKuContent = (TextView) v.findViewById(R.id.ku_content);
    }
}
