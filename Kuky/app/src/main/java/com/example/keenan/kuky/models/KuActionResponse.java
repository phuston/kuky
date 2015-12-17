package com.example.keenan.kuky.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hieunguyen on 12/7/15.
 */
public class KuActionResponse {

    @SerializedName("Status")
    private String status;

    public KuActionResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
