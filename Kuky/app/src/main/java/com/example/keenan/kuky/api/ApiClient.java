package com.example.keenan.kuky.api;

import android.util.Log;

import retrofit.RestAdapter;

public class ApiClient {
    private static KukyApiEndpointInterface sKukyApiClient;

    public static KukyApiEndpointInterface getKukyApiClient() {
        if (sKukyApiClient == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://10.0.2.2:3000")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new RestAdapter.Log() {
                        @Override
                        public void log(String msg) {
                            String[] blacklist = {"Access-Control", "Cache-Control", "Connection", "Content-Type", "Keep-Alive", "Pragma", "Server", "Vary", "X-Powered-By"};
                            for (String bString : blacklist) {
                                if (msg.startsWith(bString)) {
                                    return;
                                }
                            }
                            Log.d("Retrofit", msg);
                        }
                    })
                    .build();

            sKukyApiClient = restAdapter.create(KukyApiEndpointInterface.class);
        }

        return sKukyApiClient;
    }
}