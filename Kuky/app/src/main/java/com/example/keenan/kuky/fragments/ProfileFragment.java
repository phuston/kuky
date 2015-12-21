package com.example.keenan.kuky.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.keenan.kuky.R;
import com.example.keenan.kuky.adapters.KuCardAdapter;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.helpers.AuthHelper;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.User;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Profile Fragment is one tab of the KuView activity. It holds information about the user, such as
 * their 'kudos' or score. There are two lists of kus under this view, the users generated kus,
 * as well as the ones that the user favorited.
 */
public class ProfileFragment extends Fragment {

    public static final int VOTING_SCORE = 5;
    public static final int KU_COMPOSING_SCORE = 20;
    public static final int COMMENTING_SCORE = 10;

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private User user;
    private static KuCardAdapter mKuCardAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final ArrayList<Ku> localFavoriteKus = new ArrayList<>();
    private static int localScore = 0;

    @Bind(R.id.ku_profile_feed) RecyclerView mKuRecyclerView;
    @Bind(R.id.kudos_display) TextView kudosDisplay;
    @Bind(R.id.favorite_kus_profile) ImageButton favoritesButton;
    @Bind(R.id.composed_kus_profile) ImageButton composedButton;
    @Bind(R.id.lack_of_kus) TextView mNoKusTextProfile;

    /**
     * Clicking the tab at the top of the screen, updates UI and datasets
     * @param view the current screen view
     */
    @OnClick(R.id.favorite_kus_profile)
    public void onFavoritesSelected(View view) {
        if (user != null) {
            mKuCardAdapter.setList(localFavoriteKus);
            mKuCardAdapter.notifyDataSetChanged();
        }
        kudosDisplay.setText("Your Kudos: " + String.valueOf(localScore));
        favoritesButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.cyan_A700));
        composedButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_100));
    }

    /**
     * Clicking the tab at the top of the view, switches datasets and UI
     * @param view the current screen view
     */
    @OnClick(R.id.composed_kus_profile)
    public void onComposedSelected(View view) {
        if (user != null) {
            mKuCardAdapter.setList(user.getComposedKus());
            mKuCardAdapter.notifyDataSetChanged();
        }
        kudosDisplay.setText("Your Kudos: " + String.valueOf(localScore));
        composedButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.cyan_A700));
        favoritesButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_100));
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * calls the super oncreate
     * @param savedInstanceState a previous saved state that can be reconstructed
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets up the view of the fragment as well as the recycler view. Calls the
     * updateProfile() method to load data into the view
     * @param inflater inflates the view to the fragment
     * @param container the parent view that the UI should be attached to
     * @param savedInstanceState a previous saved state that can be attached to
     * @return the rootView of the the fragments view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);

        updateProfile();

        mKuRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mKuRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }

    /**
     * Makes an API call to pull the favorited kus as well as setting the users 'kudos' or score
     * Uses the KuCardAdapter class for button functionality and loading data
     */
    public void updateProfile() {
        String[] creds = AuthHelper.getCreds(getContext());
        ApiClient.getKukyApiClient(creds)
            .getUser(creds[0])
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<User>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG + ": Retrofit Error - ", e.toString());
                }

                @Override
                public void onNext(User userResponse) {
                    user = userResponse;
                    localFavoriteKus.addAll(user.getFavoritedKus());
                    updateScore(user.getScore());
                    kudosDisplay.setText("Your Kudos: " + String.valueOf(localScore));
                    checkForKus(localFavoriteKus);
                    Log.wtf(TAG, user.getFavoritedKus().toString());
                    mKuCardAdapter = new KuCardAdapter(localFavoriteKus, getActivity());
                    mKuRecyclerView.setAdapter(mKuCardAdapter);
                }
            });
    }

    /**
     * Checks for empty list of kus, if so, handles empty set by showing 'sorry' text
     * @param mkuList List of haikus
     */
    public void checkForKus(ArrayList mkuList) {
        if (mkuList.isEmpty()) {
            mKuRecyclerView.setVisibility(View.GONE);
            mNoKusTextProfile.setVisibility(View.VISIBLE);
        }
        else {
            mKuRecyclerView.setVisibility(View.VISIBLE);
            mNoKusTextProfile.setVisibility(View.GONE);
        }
    }

    /**
     * Updates the favorited kus into the dataset list
     * @param ku Ku Object
     * @param add Boolean function if adding to ku list
     */
    public static void updateFavorite(Ku ku, boolean add) {
        Log.wtf(TAG, "UPDATING LOCAL FAVORITES " + String.valueOf(add));
        if (add) {
            localFavoriteKus.add(ku);
            mKuCardAdapter.notifyDataSetChanged();
            Log.wtf(TAG, localFavoriteKus.toString());
        } else {
            localFavoriteKus.remove(ku);
            mKuCardAdapter.notifyDataSetChanged();
            Log.wtf(TAG, localFavoriteKus.toString());
        }
    }

    public static void updateScore(int delta) {
        localScore += delta;
    }
}
