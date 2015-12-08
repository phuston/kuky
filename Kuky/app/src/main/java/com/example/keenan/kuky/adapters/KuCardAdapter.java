package com.example.keenan.kuky.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.activities.LoginActivity;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.KuActionRequest;
import com.example.keenan.kuky.models.KuActionResponse;

import java.util.ArrayList;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class KuCardAdapter extends RecyclerView.Adapter<KuViewHolder>{

    private static final String TAG = KuCardAdapter.class.getSimpleName();
    private ArrayList<Ku> mDataset;
    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public KuCardAdapter(ArrayList<Ku> Kus, Context context) {
        mDataset = Kus;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public KuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ku_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ButterKnife.bind(this, v);
        return new KuViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(KuViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

//        String kuContent = mDataset.get(position).getContent();

        holder.setClickListener(new KuViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos) {
                String id = Integer.toString(mDataset.get(pos).getId());
                Toast.makeText(mContext, id, Toast.LENGTH_LONG).show();
            }
        });

        final Ku mKu = mDataset.get(position);

        String[] lines = mKu.getContent();
        Integer ku_karma = mKu.getKarma();

        holder.vKuContent1.setText(lines[0]);
        holder.vKuContent2.setText(lines[1]);
        holder.vKuContent3.setText(lines[2]);
        holder.vKuKarma.setText(String.valueOf(ku_karma));

        holder.vUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getUserId();
                int kuId = mKu.getId();
                Log.d(TAG, new KuActionRequest(userId, kuId).toString());
                if (userId > 0) {
                    ApiClient.getKukyApiClient().upvoteKu(new KuActionRequest(userId, kuId))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<KuActionResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(KuActionResponse kuActionResponse) {
                                    Log.d(TAG, kuActionResponse.getStatus());
                                }
                            });
                }
            }
        });

        holder.vDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getUserId();
                int kuId = mKu.getId();
                Log.d(TAG, new KuActionRequest(userId, kuId).toString());
                if (userId > 0) {
                    ApiClient.getKukyApiClient().downvoteKu(new KuActionRequest(userId, kuId))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<KuActionResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(KuActionResponse kuActionResponse) {
                                    Log.d(TAG, kuActionResponse.getStatus());
                                }
                            });
                }
            }
        });

        holder.vFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getUserId();
                int kuId = mKu.getId();
                Log.d(TAG, new KuActionRequest(userId, kuId).toString());
                if (userId > 0) {
                    ApiClient.getKukyApiClient().favoriteKu(new KuActionRequest(userId, kuId))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<KuActionResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(KuActionResponse kuActionResponse) {
                                    Log.d(TAG, kuActionResponse.getStatus());
                                }
                            });
                }
            }
        });
    }

    public int getUserId() {
        SharedPreferences settings = mContext.getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        return settings.getInt("userId", -1);
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

    public void setList(ArrayList<Ku> newList) {
        mDataset = newList;
    }
}
