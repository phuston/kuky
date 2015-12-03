package com.example.keenan.kuky.api;

import retrofit.RestAdapter;

public class ApiClient {
    private static KukyApiEndpointInterface sKukyApiClient;

    public static KukyApiEndpointInterface getKukyApiClient() {
        if (sKukyApiClient == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://localhost:3000")
                    .build();

            sKukyApiClient = restAdapter.create(KukyApiEndpointInterface.class);
        }

        return sKukyApiClient;
    }
}
