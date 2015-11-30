package com.example.keenan.kuky.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.keenan.kuky.R;

public class KuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected CardView vCardView;
    protected TextView vKuContent;

    private ClickListener vClickListener;

    public KuViewHolder(View v) {
        super(v);
        vCardView = (CardView) v.findViewById(R.id.ku_card_view);
        vKuContent = (TextView) v.findViewById(R.id.ku_content);

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
