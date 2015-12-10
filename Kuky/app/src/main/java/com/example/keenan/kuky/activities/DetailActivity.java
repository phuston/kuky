package com.example.keenan.kuky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.models.Comment;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.KuDetailResponse;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    public static final String KU_ID = "ku_id";
    private int ku_id;
    private Ku mKu;
    private ArrayList<Comment> mComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String ku_id = intent.getStringExtra(DetailActivity.KU_ID);

        fetchKuInfo(ku_id);


        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
