package com.example.keenan.kuky.models;


public class Comment {

    private Integer id;
    private String content;
    private Integer upvotes;
    private Integer downvotes;


    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }
}
