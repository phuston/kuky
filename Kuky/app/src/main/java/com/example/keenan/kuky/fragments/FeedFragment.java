package com.example.keenan.kuky.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.adapters.KuCardAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FeedFragment extends Fragment {

    private String[] mKus = {"this is my first ku", "This is my second ku", "This is my third ku", "This is my fourth ku", "This is my fifth ku", "This is my sixth ku", "This is my seventh ku", "This is my eigth ku", "this is my ninth ku", "this is my tenth ku"};

    private KuCardAdapter mKuCardAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.ku_feed_rv) RecyclerView mKuRecyclerView;

    @Bind(R.id.hotButton) Button mHotButton;
    @Bind(R.id.topButton) Button mTopButton;
    @Bind(R.id.recentButton) Button mRecentButton;

    @OnClick(R.id.hotButton)
    public void onHotButtonClicked(View view) {
        // Make call to reload the data in the correct order
        Snackbar.make(view, "Showing hottest Kus!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.topButton)
    public void onTopButtonClicked(View view) {
        // Make call to reload the data in the correct order
        Snackbar.make(view, "Showing top Kus!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.recentButton)
    public void onRecentButtonClicked(View view) {
        //Make call to reload the data in the correct order
        Snackbar.make(view, "Showing recent Kus!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, rootView);

        mKuRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mKuRecyclerView.setLayoutManager(mLayoutManager);

        mKuCardAdapter = new KuCardAdapter(mKus);
        mKuRecyclerView.setAdapter(mKuCardAdapter);




        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {

    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
