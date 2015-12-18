package com.example.keenan.kuky.models;

public class CommentComposeRequest {

    private String comment;
    private int kuId;
    private int userId;

    public CommentComposeRequest (String comment, int kuId, int userId) {
        this.comment = comment;
        this.kuId = kuId;
        this.userId = userId;
    }

    public String getContent() {
        return comment;
    }

    public int getKuId() {
        return kuId;
    }

    public int getUserId() {
        return userId;
    }

}