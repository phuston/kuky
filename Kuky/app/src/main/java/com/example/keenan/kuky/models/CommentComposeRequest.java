package com.example.keenan.kuky.models;

public class CommentComposeRequest {

    private String Content;
    private int kuId;
    private int userId;

    public CommentComposeRequest (String Content, int kuId, int userId) {
        this.Content = Content;
        this.kuId = kuId;
        this.userId = userId;
    }

    public String getContent() {
        return Content;
    }

    public int getKuId() {
        return kuId;
    }

    public int getUserId() {
        return userId;
    }

}