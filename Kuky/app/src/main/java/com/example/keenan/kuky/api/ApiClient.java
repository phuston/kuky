package com.example.keenan.kuky.api;

import retrofit.RestAdapter;

/**
 * Created by patrick on 8/27/15.
 */

public class ApiClient {
    private static KukyApiEndpointInterface sMovieDbService;

    public static KukyApiEndpointInterface getMovieDbClient() {
        if (sMovieDbService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.themoviedb.org/3")
                    .build();

            sMovieDbService = restAdapter.create(KukyApiEndpointInterface.class);
        }

        return sMovieDbService;
    }
}
