package com.example.keenan.kuky.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.keenan.kuky.R;


public class KuCardAdapter extends RecyclerView.Adapter<KuViewHolder>{
    private String[] mDataset;
    private Context mContext;


    // Provide a suitable constructor (depends on the kind of dataset)
    public KuCardAdapter(String[] Kus, Context context) {
        mDataset = Kus;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public KuViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ku_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        KuViewHolder vh = new KuViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(KuViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.setClickListener(new KuViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos){
                String Ku = mDataset[pos];
                Toast.makeText(mContext, Ku, Toast.LENGTH_LONG).show();
            }
        });

        holder.vKuContent.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.length;
    }
}
