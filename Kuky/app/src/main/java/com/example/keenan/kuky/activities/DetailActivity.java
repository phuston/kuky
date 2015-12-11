package com.example.keenan.kuky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.models.Comment;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.KuDetailResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    public static final String KU_ID = "ku_id";
    private int ku_id;
    private Ku mKu;
    private ArrayList<Comment> mComments;

    @Bind(R.id.detail_back_button) Button mBackButton;
    @Bind(R.id.ku_card_detail_view) CardView mKuCard;
    @Bind(R.id.comment_submit_button) Button mCommentSubmitButton;
    @Bind(R.id.edit_comment_text) EditText mEditText;
    @Bind(R.id.comment_feed_rv) RecyclerView mCommentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String ku_id = intent.getStringExtra(DetailActivity.KU_ID);
//        ButterKnife.bind(this);

        fetchKuInfo(ku_id);


        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Compose a New Ku!", Snackbar.LENGTH_LONG)
                        .setAction("New Ku!", null).show();
            }
        });
    }

    @OnClick(R.id.detail_back_button)
    public void onDetailBackButtonClick(View view) {

        //TODO: Go back to the FeedFragment

    }

    @OnClick(R.id.comment_submit_button)
    public void onCommentSubmitButton(View view) {
        Snackbar.make(view, "Your comment is submitted", Snackbar.LENGTH_LONG)
                .setAction("Submit", null).show();
    }




    public void fetchKuInfo(String ku_id){
        ApiClient.getKukyApiClient().getKuDetail(ku_id)
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
                                   mKu = kuDetailResponse.getKu();
                                   mComments = kuDetailResponse.getComments();
                               }
                           }
                );
    }

}
