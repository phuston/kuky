package com.mobproto.keenan.kuky.models;

import com.google.gson.annotations.SerializedName;

/**
 * Model for response from ku upvote/downvote requests
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
