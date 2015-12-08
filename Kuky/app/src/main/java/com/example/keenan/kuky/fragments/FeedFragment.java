package com.example.keenan.kuky.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.adapters.KuCardAdapter;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.KuRequest;
import com.example.keenan.kuky.models.KuResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FeedFragment extends Fragment {

    private static final String TAG = FeedFragment.class.getSimpleName();

//    private String[] mKus = {"this is my first ku", "This is my second ku", "This is my third ku", "This is my fourth ku", "This is my fifth ku", "This is my sixth ku", "This is my seventh ku", "This is my eigth ku", "this is my ninth ku", "this is my tenth ku"};

    private KuCardAdapter mKuCardAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Ku ku1 = new Ku(1, "This is my first ku; I really like it so much; do you like it too", 120, 30, 90, 42.294, -71.303, "today", "now");
    Ku ku2 = new Ku(2, "waiting for the train; wet ones on my hairy legs; slowly passing gas", 80, 39, 41, 42.294, -71.303, "today", "now");
    Ku ku3 = new Ku(3, "Hippopotamus; anti-Hippopotamus; annihilation", 75, 5, 70, 42.294, -71.303, "today", "now");
    Ku ku4 = new Ku(4, "Haikus are easy; but sometimes they don't make sense; refrigerator", 254, 28, 226, 42.294, -71.303, "today", "now");
    Ku ku5 = new Ku(5, "fat man sees small door; he knows he cannot fit through; tears flow freely now", 9, 3, 6, 42.294, -71.303, "today", "now");

    ArrayList<Ku> mkuList = new ArrayList<>();

    @Bind(R.id.ku_feed_rv) RecyclerView mKuRecyclerView;
    @Bind(R.id.hotButton) Button mHotButton;
    @Bind(R.id.topButton) Button mTopButton;
    @Bind(R.id.recentButton) Button mRecentButton;

    @OnClick(R.id.hotButton)
    public void onHotButtonClicked(View view) {
        UpdateKus(KuRequest.KU_SORT_HOT);
        Snackbar.make(view, "Showing hottest Kus!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.topButton)
    public void onTopButtonClicked(View view) {
        Snackbar.make(view, "Showing top Kus!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.recentButton)
    public void onRecentButtonClicked(View view) {
        UpdateKus(KuRequest.KU_SORT_RECENT);
        Snackbar.make(view, "Showing recent Kus!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateKus(KuRequest.KU_SORT_HOT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, rootView);

        mKuRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mKuRecyclerView.setLayoutManager(mLayoutManager);

        mKuCardAdapter = new KuCardAdapter(mkuList, getActivity());

        mKuRecyclerView.setAdapter(mKuCardAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {

    }

    public void UpdateKus(String sort){
        ApiClient.getKukyApiClient().getKus(sort)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<KuResponse>() {
                    @Override
                    public final void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("KukyAPI Error", e.getMessage());
                    }

                    @Override
                    public final void onNext(KuResponse response) {
                        mKuCardAdapter.updateKus(response.getKus());
                        mKuCardAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Received data");
                    }
                });
    }
}
