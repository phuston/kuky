package com.example.keenan.kuky.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.models.ShortKu;
import com.example.keenan.kuky.models.User;
import com.example.keenan.kuky.models.UserProfileResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private ArrayList<ShortKu> composedKus = new ArrayList<>();
    private ArrayList<ShortKu> favoritedKus = new ArrayList<>();
    private User user;
    private OnFragmentInteractionListener mListener;

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
        return rootView;
    }

    public void updateProfile() {
        ApiClient.getKukyApiClient().getUser("thecardkid")
            .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserProfileResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserProfileResponse userProfileResponse) {
                        Log.d(TAG, userProfileResponse.toString());
                        processKus(userProfileResponse.getComposedKus(), "composed");
                        processKus(userProfileResponse.getFavoritedKus(), "favorited");
                        processUserInfo(userProfileResponse.getBasicInfo());
                    }
                });
    }

    public void processKus(JsonObject composed, String type) {
        for (Map.Entry<String, JsonElement> entry: composed.entrySet()) {
            Log.d(TAG, entry.getKey());
            JsonObject ku = entry.getValue().getAsJsonObject();
            int id = Integer.parseInt(entry.getKey());
            String content = ku.get("content").getAsString();
            int karma = ku.get("karma").getAsInt();
            double lat = ku.get("lat").getAsDouble();
            double lon = ku.get("lon").getAsDouble();
            if (type.equals("composed")) {
                composedKus.add(new ShortKu(id, content, karma, lat, lon));
            } else {
                favoritedKus.add(new ShortKu(id, content, karma, lat, lon));
            }
        }
    }

    public void processUserInfo(JsonObject userInfo) {
        user = new User(userInfo.get("id").getAsInt(),
                userInfo.get("username").getAsString(),
                userInfo.get("score").getAsInt(),
                userInfo.get("radiusLimit").getAsDouble());
        Log.d(TAG, user.toString());
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
