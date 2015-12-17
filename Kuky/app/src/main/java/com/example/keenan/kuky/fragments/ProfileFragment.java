package com.example.keenan.kuky.fragments;

import android.content.SharedPreferences;
import android.net.Uri;
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
import com.example.keenan.kuky.activities.LoginActivity;
import com.example.keenan.kuky.adapters.KuCardAdapter;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.helpers.AuthHelper;
import com.example.keenan.kuky.models.Ku;
import com.example.keenan.kuky.models.User;
import com.example.keenan.kuky.models.UserProfileResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private User user;
    private KuCardAdapter mKuCardAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final ArrayList<Ku> localFavoriteKus = new ArrayList<>();
    private static final ArrayList<Ku> localComposedKus = new ArrayList<>();

    @Bind(R.id.ku_profile_feed) RecyclerView mKuRecyclerView;
    @Bind(R.id.kudos_display) TextView kudosDisplay;
    @Bind(R.id.favorite_kus_profile) ImageButton favoritesButton;
    @Bind(R.id.composed_kus_profile) ImageButton composedButton;
    @Bind(R.id.lack_of_kus) TextView mNoKusTextProfile;

    @OnClick(R.id.favorite_kus_profile)
    public void onFavoritesSelected(View view) {
        if (user != null) {
            mKuCardAdapter.setList(localFavoriteKus);
            mKuCardAdapter.notifyDataSetChanged();
        }
        favoritesButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_100));
        composedButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_100));
    }

    @OnClick(R.id.composed_kus_profile)
    public void onComposedSelected(View view) {
        if (user != null) {
            mKuCardAdapter.setList(user.getComposedKus());
            mKuCardAdapter.notifyDataSetChanged();
        }
        composedButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_100));
        favoritesButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey_100));
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

                }

                @Override
                public void onNext(User userResponse) {
                    user = userResponse;
                    localFavoriteKus.addAll(user.getFavoritedKus());
                    Log.wtf(TAG, user.getFavoritedKus().toString());
                    mKuCardAdapter = new KuCardAdapter(localFavoriteKus, getActivity());
                    mKuRecyclerView.setAdapter(mKuCardAdapter);
                }
            });
    }

    public void checkForKus(ArrayList mkuList) {
        if (mkuList.isEmpty())
        {
            mKuRecyclerView.setVisibility(View.GONE);
            mNoKusTextProfile.setVisibility(View.VISIBLE);
        }
        else {
            mKuRecyclerView.setVisibility(View.VISIBLE);
            mNoKusTextProfile.setVisibility(View.GONE);
        }
    }

    public static void updateFavorite(Ku ku, boolean add) {
        Log.wtf(TAG, "UPDATING LOCAL FAVORITES " + String.valueOf(add));
        if (add) {
            localFavoriteKus.add(ku);
            Log.wtf(TAG, localFavoriteKus.toString());
        } else {
            localFavoriteKus.remove(ku);
            Log.wtf(TAG, localFavoriteKus.toString());
        }
    }

    public static void addToComposed(Ku ku) {
        localComposedKus.add(ku);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
