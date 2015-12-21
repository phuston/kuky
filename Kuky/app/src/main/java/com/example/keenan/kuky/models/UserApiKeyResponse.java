package com.example.keenan.kuky.models;

import com.google.gson.annotations.SerializedName;

/**
 * Model for response from user login/register request
 */
public class UserApiKeyResponse {
    @SerializedName("newKey")
    private String newKey;

    @SerializedName("userId")
    private int userId;

    @SerializedName("error")
    private String errorMessage;

    public UserApiKeyResponse(String newKey, int userId, String errorMessage) {
        this.newKey = newKey;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
