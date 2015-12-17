package com.example.keenan.kuky.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.activities.LoginActivity;
import com.example.keenan.kuky.adapters.KuCardAdapter;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.helpers.AuthHelper;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.KuRequest;
import com.example.keenan.kuky.models.KuListResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FeedFragment extends Fragment {

    private static final String TAG = FeedFragment.class.getSimpleName();

    private KuCardAdapter mKuCardAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Ku> mkuList = new ArrayList<>();

    @Bind(R.id.ku_feed_rv) RecyclerView mKuRecyclerView;
    @Bind(R.id.hotButton) ImageButton mHotButton;
    @Bind(R.id.recentButton) ImageButton mRecentButton;
    @Bind(R.id.lack_of_kus) TextView mNoKusText;

    @OnClick(R.id.hotButton)
    public void onHotButtonClicked(View view) {
        UpdateKus(KuRequest.KU_SORT_HOT);
        mHotButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_100));
        mRecentButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_100));
        Snackbar.make(view, "Showing hottest Kus!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.recentButton)
    public void onRecentButtonClicked(View view) {
        UpdateKus(KuRequest.KU_SORT_RECENT);
        mHotButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_100));
        mRecentButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_100));
        Snackbar.make(view, "Showing recent Kus!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateKus(KuRequest.KU_SORT_RECENT);
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

    public void UpdateKus(String sort) {
        ApiClient.getKukyApiClient(AuthHelper.getCreds(getContext()))
                .getKus(sort)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<KuListResponse>() {
                    @Override
                    public final void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("KukyAPI Error", e.getMessage());
                    }

                    @Override
                    public final void onNext(KuListResponse response) {
                        mkuList = response.getKus();
                        checkForKus(mkuList);
                        mKuCardAdapter.setList(mkuList);
                        mKuCardAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Received data");
                    }
                });
    }

    public void checkForKus(ArrayList mkuList)
    {
        if (mkuList.isEmpty())
        {
            mKuRecyclerView.setVisibility(View.GONE);
            mNoKusText.setVisibility(View.VISIBLE);
        }
        else {
            mKuRecyclerView.setVisibility(View.VISIBLE);
            mNoKusText.setVisibility(View.GONE);
        }
    }
}
