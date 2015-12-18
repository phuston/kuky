package com.example.keenan.kuky.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.adapters.CommentCardAdapter;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.helpers.AuthHelper;
import com.example.keenan.kuky.models.Comment;
import com.example.keenan.kuky.models.CommentComposeRequest;
import com.example.keenan.kuky.models.CommentComposeResponse;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.KuActionRequest;
import com.example.keenan.kuky.models.KuActionResponse;
import com.example.keenan.kuky.models.KuDetailResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    public static final String KU_ID = "ku_id";
    private int ku_id;
    private String mComment = "";
    private Ku mKu;
    private ArrayList<Comment> mCommentList = new ArrayList<Comment>();
    private RecyclerView.LayoutManager mLayoutManager;
    private CommentCardAdapter mCommentCardAdapter;

    @Bind(R.id.ku_content1_tv) TextView mKuContent1Tv;
    @Bind(R.id.ku_content2_tv) TextView mKuContent2Tv;
    @Bind(R.id.ku_content3_tv) TextView mKuContent3Tv;
    @Bind(R.id.ku_karma_tv) TextView mKuKarmaTv;
    @Bind(R.id.ku_card_detail_view) CardView mKuCard;
    @Bind(R.id.comment_feed_rv) RecyclerView mCommentRecyclerView;
    @Bind(R.id.lack_of_comments) TextView mNoCommentsText;
    @Bind(R.id.downvoteButtonDetail) ImageButton mDownVoteButton;
    @Bind(R.id.upvoteButtonDetail) ImageButton mUpVoteButton;
    @Bind(R.id.favoriteButtonDetail) ImageButton mFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_logo_clear);

        Intent intent = getIntent();
        final String ku_id = intent.getStringExtra(DetailActivity.KU_ID);

        Log.d(TAG, "DetailActivity received Ku_ID of : " + ku_id);

        fetchKuInfo(ku_id);

        mCommentRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mCommentRecyclerView.setLayoutManager(mLayoutManager);

        Log.d(TAG, Integer.toString(mCommentList.size()));

        mCommentCardAdapter = new CommentCardAdapter(mCommentList, this, Integer.parseInt(ku_id));
        mCommentRecyclerView.setAdapter(mCommentCardAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Enter Your Comment:");

                final EditText input = new EditText(DetailActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Comment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mComment = input.getText().toString();
                        int userId = getUserId();

                        CommentComposeRequest mCommentRequest = new CommentComposeRequest(mComment, Integer.parseInt(ku_id), userId);

                        ApiClient.getKukyApiClient(AuthHelper.getCreds(DetailActivity.this))
                                .postComment(mCommentRequest)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<CommentComposeResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG + ": Retrofit Error - ", e.toString());
                                    }

                                    @Override
                                    public void onNext(CommentComposeResponse commentComposeResponse) {
                                        Log.wtf(TAG, commentComposeResponse.getComment().getContent());
                                        mCommentList.add(commentComposeResponse.getComment());
                                        mCommentCardAdapter.setList(mCommentList);
                                        mCommentCardAdapter.notifyDataSetChanged();
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


    }

    public void fetchKuInfo(String ku_id){
        ApiClient.getKukyApiClient().getKuDetail(ku_id, String.valueOf(getUserId()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<KuDetailResponse>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(KuDetailResponse kuDetailResponse) {
                                   mCommentList = kuDetailResponse.getComments();
                                   mCommentCardAdapter.setList(mCommentList);
                                   mCommentCardAdapter.notifyDataSetChanged();
                                   checkForComments(mCommentList);
                                   mKu = kuDetailResponse.getKu();
                                   Log.d(TAG, mCommentList.get(0).toString());
                                   Log.d(TAG, mKu.toString());
                                   setKuCardViewContent(mKu);
                               }
                           }
                );
    }

    public void setKuCardViewContent(Ku ku){
        String[] ku_content = ku.getContent();
        mKuContent1Tv.setText(ku_content[0]);
        mKuContent2Tv.setText(ku_content[1]);
        mKuContent3Tv.setText(ku_content[2]);
        mKuKarmaTv.setText(String.valueOf(ku.getKarma()));

//        boolean isUpvoted = ku.getUpvoted();
//        boolean isDownvoted = ku.getDownvoted();
//        boolean isFavorited = ku.getFavorited();
//
//        if (isFavorited) {
//            mFavoriteButton.setImageResource(R.drawable.ic_star_yellow_900_24dp);
//        } else {
//            mFavoriteButton.setImageResource(R.drawable.ic_star_outline_black_24dp);
//        }
//
//        if (isUpvoted) {
//            mUpVoteButton.setBackgroundResource(R.drawable.ic_arrow_drop_up_blue_800_24dp);
//        } else {
//            mUpVoteButton.setBackgroundResource(R.drawable.ic_arrow_drop_up_black_24dp);
//        }
//
//        if (isDownvoted) {
//            mDownVoteButton.setBackgroundResource(R.drawable.ic_arrow_drop_down_blue_800_24dp);
//        } else {
//            mDownVoteButton.setBackgroundResource(R.drawable.ic_arrow_drop_down_black_24dp);
//        }
    }
    public void checkForComments(ArrayList mCommentList) {

        if (mCommentList.isEmpty())
        {
            mCommentRecyclerView.setVisibility(View.GONE);
            mNoCommentsText.setVisibility(View.VISIBLE);
        }
        else {
            mCommentRecyclerView.setVisibility(View.VISIBLE);
            mNoCommentsText.setVisibility(View.GONE);
        }
    }

    public int getUserId() {
        SharedPreferences settings = this.getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        return settings.getInt("userId", -1);
    }

    public void sendUpvoteRequest(KuActionRequest request, final Ku ku) {
        ApiClient.getKukyApiClient(AuthHelper.getCreds(getApplicationContext()))
                .upvoteKu(request)
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
                    }
                });
    }

    public void sendDownvoteRequest(KuActionRequest request, final Ku ku) {
        ApiClient.getKukyApiClient(AuthHelper.getCreds(getApplicationContext()))
                .downvoteKu(request)
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
                    }
                });
    }
}
