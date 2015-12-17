package com.example.keenan.kuky.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.activities.DetailActivity;
import com.example.keenan.kuky.activities.LoginActivity;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.fragments.ProfileFragment;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.KuActionRequest;
import com.example.keenan.kuky.models.KuActionResponse;

import java.util.ArrayList;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class KuCardAdapter extends RecyclerView.Adapter<KuViewHolder>{

    //TODO: Update view whenever upvote/downvote/favorite request is sent

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
    public void onBindViewHolder(final KuViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.setClickListener(new KuViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos) {
                String id = Integer.toString(mDataset.get(pos).getId());
                Log.d(TAG, "ID should be " + id);
                Intent dIntent = new Intent(mContext, DetailActivity.class);
                dIntent.putExtra(DetailActivity.KU_ID, id);
                mContext.startActivity(dIntent);
            }
        });

        final Ku mKu = mDataset.get(position);

        String[] lines = mKu.getContent();
        Integer ku_karma = mKu.getKarma();

        holder.vKuContent1.setText(lines[0]);
        holder.vKuContent2.setText(lines[1]);
        holder.vKuContent3.setText(lines[2]);
        holder.vKuKarma.setText(String.valueOf(ku_karma));
        holder.vUpvotePressed = mKu.getUpvoted();
        holder.vDownvotePressed = mKu.getDownvoted();
        holder.vFavoritePressed = mKu.getFavorited();

        holder.vUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getUserId();
                int kuId = mKu.getId();

                holder.vUpvotePressed = !holder.vUpvotePressed;
                if (userId > 0) {
                    sendUpvoteRequest(new KuActionRequest(userId, kuId), mKu, position);
                    if (holder.vDownvotePressed) {
                        holder.vDownvotePressed = false;
                        sendDownvoteRequest(new KuActionRequest(userId, kuId), mKu, position);
                    }
                }
            }
        });

        holder.vDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getUserId();
                int kuId = mKu.getId();

                holder.vDownvotePressed = !holder.vDownvotePressed;
                if (userId > 0) {
                    sendDownvoteRequest(new KuActionRequest(userId, kuId), mKu, position);
                    if (holder.vUpvotePressed) {
                        holder.vUpvotePressed = false;
                        sendUpvoteRequest(new KuActionRequest(userId, kuId), mKu, position);
                    }
                }
            }
        });

        holder.vFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getUserId();
                int kuId = mKu.getId();

                holder.vFavoritePressed = !holder.vFavoritePressed;
                if (userId > 0) {
                    ApiClient.getKukyApiClient().favoriteKu(new KuActionRequest(userId, kuId))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<KuActionResponse>() {
                                @Override
                                public void onCompleted() {}

                                @Override
                                public void onError(Throwable e) {}

                                @Override
                                public void onNext(KuActionResponse kuActionResponse) {
                                    Log.d(TAG, kuActionResponse.getStatus());
                                    Log.d(TAG, String.valueOf(holder.vFavoritePressed));
                                    ProfileFragment.updateFavorite(mKu, holder.vFavoritePressed);
                                }
                            });
                }
            }
        });
    }

    public void sendUpvoteRequest(KuActionRequest request, final Ku ku, final int position) {
        ApiClient.getKukyApiClient().upvoteKu(request)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<KuActionResponse>() {
                @Override
                public void onCompleted() {}

                @Override
                public void onError(Throwable e) {}

                @Override
                public void onNext(KuActionResponse kuActionResponse) {
                    Log.wtf(TAG, kuActionResponse.getStatus());
                    ku.setKarma(Integer.parseInt(kuActionResponse.getStatus()));
                    notifyItemChanged(position);
                }
            });
    }

    public void sendDownvoteRequest(KuActionRequest request, final Ku ku, final int position) {
        ApiClient.getKukyApiClient().downvoteKu(request)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<KuActionResponse>() {
                @Override
                public void onCompleted() {}

                @Override
                public void onError(Throwable e) {}

                @Override
                public void onNext(KuActionResponse kuActionResponse) {
                    Log.wtf(TAG, kuActionResponse.getStatus());
                    ku.setKarma(Integer.parseInt(kuActionResponse.getStatus()));
                    notifyItemChanged(position);
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
