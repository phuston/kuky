package com.example.keenan.kuky.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hieunguyen on 12/3/15.
 */
public class UserApiKeyResponse {
    @SerializedName("newKey")
    private String newKey;

    @SerializedName("error")
    private String errorMessage;

    public UserApiKeyResponse(String newKey, String errorMessage) {
        this.newKey = newKey;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "UserApiKeyResponse{" +
                "newKey='" + newKey + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public String getNewKey() {
        return newKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
