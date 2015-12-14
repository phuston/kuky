package com.example.keenan.kuky.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.activities.LoginActivity;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.models.Comment;
import com.example.keenan.kuky.models.CommentActionRequest;
import com.example.keenan.kuky.models.CommentActionResponse;

import java.util.ArrayList;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by patrick on 12/9/15.
 */
public class CommentCardAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private static final String TAG = CommentCardAdapter.class.getSimpleName();
    private ArrayList<Comment> mDataset;
    private Context mContext;
    private int mku_id;

    public CommentCardAdapter(ArrayList<Comment> Comments, Context context, int kuId) {
        mDataset = Comments;
        mContext = context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_card_view, parent, false);
        ButterKnife.bind(this, v);
        return new CommentViewHolder(v);
    }

    public void onBindViewHolder(CommentViewHolder holder, final int position) {
        holder.setClickListener(new CommentViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int position) {
                // TODO: Do something here? Maybe not actually, idk what it would do
            }
        });

        final Comment mComment = mDataset.get(position);

        String content = mComment.getContent();
        // TODO: Get other relevant shit on the comment

        holder.vCommentContent.setText(content);

        holder.vUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getUserId();
                int commentId = mComment.getId();
                Log.d(TAG, new CommentActionRequest(userId, mku_id, commentId).toString());
                if (userId > 0) {
                    ApiClient.getKukyApiClient().upvoteComment(new CommentActionRequest(userId, mku_id, commentId))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<CommentActionResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(CommentActionResponse CommentActionResponse) {

                                }
                            });
                }
            }
        });

        holder.vDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getUserId();
                int commentId = mComment.getId();
                Log.d(TAG, new CommentActionRequest(userId, mku_id, commentId).toString());
                if (userId > 0) {
                    ApiClient.getKukyApiClient().downvoteComment(new CommentActionRequest(userId, mku_id, commentId))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<CommentActionResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(CommentActionResponse CommentActionResponse) {

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

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setList(ArrayList<Comment> newList) {
        mDataset = newList;
    }
}
