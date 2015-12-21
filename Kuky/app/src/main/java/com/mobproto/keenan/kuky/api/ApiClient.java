package com.mobproto.keenan.kuky.api;

import android.util.Base64;
import android.util.Log;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ApiClient {
    private static KukyApiEndpointInterface sKukyApiClient;

    /**
     * Return an API endpoint client using credentials for all authenticated endpoints
     * @param creds user credentials
     * @return sKukyApiClient - authorized API client
     */
    public static KukyApiEndpointInterface getKukyApiClient(String[] creds) {
        if (creds != null) {
            String authHeader = creds[0] + ":" + creds[1];
            final String basic = "Basic " + Base64.encodeToString(authHeader.getBytes(), Base64.NO_WRAP);
            Log.wtf("API CLIENT", basic);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Authorization", basic);
                            request.addHeader("Accept", "application/json");
                        }
                    })
                    .setEndpoint("http://104.236.85.252:8080")
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

    /**
     * Returns and API endpoint client for use with all unauthorized endpoints
     * @return sKukyApiClient - unauthorized API client
     */
    public static KukyApiEndpointInterface getKukyApiClient() {
        if ((sKukyApiClient == null)) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://104.236.85.252:8080")
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
