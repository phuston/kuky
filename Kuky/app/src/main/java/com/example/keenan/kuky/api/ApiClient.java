package com.example.keenan.kuky.api;

import android.util.Base64;
import android.util.Log;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ApiClient {
    private static KukyApiEndpointInterface sKukyApiClient;

    public static KukyApiEndpointInterface getKukyApiClient(String username, String apiKey) {
        Log.wtf("API CLIENT", sKukyApiClient.toString() + ' ' + username + ' ' + apiKey);
        if ((username != null) && (apiKey != null)) {
            String creds = username + ":" + apiKey;
            final String basic = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
            Log.wtf("API CLIENT", basic);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Authorization", basic);
                            request.addHeader("Accept", "application/json");
                        }
                    })
                    .setEndpoint("http://10.0.2.2:3000")//104.236.85.252:8080")
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

    public static KukyApiEndpointInterface getKukyApiClient() {
        if ((sKukyApiClient == null)) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://10.0.2.2:3000")//104.236.85.252:8080")
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
