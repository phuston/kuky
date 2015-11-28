package com.example.keenan.kuky.models;

public class Ku {

    private Integer id;
    private String content;
    private Integer upvotes;
    private Integer downvotes;
    private Double lat;
    private Double lon;
    private String createdAt;
    private String updatedAt;

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

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}