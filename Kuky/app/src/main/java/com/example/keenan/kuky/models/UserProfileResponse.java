package com.example.keenan.kuky.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hieunguyen on 12/6/15.
 */
public class UserProfileResponse {
    @SerializedName("basicInfo")
    private JsonObject basicInfo;

    @SerializedName("composedKus")
    private JsonObject composedKus;

    @SerializedName("favoritedKus")
    private JsonObject favoritedKus;

    public UserProfileResponse(JsonObject basicInfo, JsonObject composedKus, JsonObject favoritedKus) {
        this.basicInfo = basicInfo;
        this.composedKus = composedKus;
        this.favoritedKus = favoritedKus;
    }

    public JsonObject getBasicInfo() {
        return basicInfo;
    }

    public JsonObject getComposedKus() {
        return composedKus;
    }

    public JsonObject getFavoritedKus() {
        return favoritedKus;
    }

    @Override
    public String toString() {
        return "UserProfileResponse{" +
                "basicInfo=" + basicInfo +
                ", composedKus=" + composedKus +
                ", favoritedKus=" + favoritedKus +
                '}';
    }
}
