package com.example.keenan.kuky.models;

import com.google.gson.annotations.SerializedName;

/**
 * Model for response to comment upvote/downvote requests
 */
public class CommentActionResponse {
    @SerializedName("Status")
    public String status;

    public CommentActionResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "CommentActionResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
