package com.example.keenan.kuky.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.keenan.kuky.activities.LoginActivity;

/**
 * Created by hieunguyen on 12/17/15.
 */
public class AuthHelper {

    /**
     * AuthHelper gets the user's auth information from sharedpreferences
     * @param context
     * @return
     */
    public static String[] getCreds(Context context) {
        SharedPreferences settings = context.getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String uname = settings.getString("username", null);
        String apiKey = settings.getString("apiKey", null);
        int userId = settings.getInt("userId", -1);
        if ((uname != null) && (apiKey != null) & (userId > 0)) {
            return new String[] {uname, apiKey, String.valueOf(userId)};
        } else {
            return null;
        }
    }
}
