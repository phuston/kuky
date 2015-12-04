package com.example.keenan.kuky.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.models.Ku;

import java.util.ArrayList;


public class KuCardAdapter extends RecyclerView.Adapter<KuViewHolder>{
    private ArrayList<Ku> mDataset;
    private Context mContext;


    // Provide a suitable constructor (depends on the kind of dataset)
    public KuCardAdapter(ArrayList<Ku> Kus, Context context) {
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

//        String kuContent = mDataset.get(position).getContent();

        holder.setClickListener(new KuViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos){
                String id = Integer.toString(mDataset.get(pos).getId());
                Toast.makeText(mContext, id, Toast.LENGTH_LONG).show();
            }
        });

        //TODO: Make adapter split up the content based off of <semicolons> into three lines
        Log.wtf("SIZE", Integer.toString(mDataset.size()));

        String[] lines = mDataset.get(position).getContent();

        holder.vKuContent1.setText(lines[0]);
        holder.vKuContent2.setText(lines[1]);
        holder.vKuContent3.setText(lines[2]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
