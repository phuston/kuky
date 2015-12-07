package com.example.keenan.kuky.models;

public class Ku {

    private Integer id;
    private String content;
    private Integer upvotes;
    private Integer downvotes;
    private Integer karma;
    private Double lat;
    private Double lon;
    private String createdAt;
    private String updatedAt;

    public Ku(Integer id, String content, Integer upvotes, Integer downvotes, Integer karma, Double lat, Double lon, String createdAt, String updatedAt){
        this.id = id;
        this.content = content;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.karma = karma;
        this.lat = lat;
        this.lon = lon;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Ku(Integer id, String content, Integer karma, Double lat, Double lon) {
        this.id = id;
        this.content = content;
        this.karma = karma;
        this.lat = lat;
        this.lon = lon;
    }

    public Integer getId() {
        return id;
    }

    public String[] getContent() {
        return content.split(";");
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public Integer getKarma() {
        return karma;
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

    @Override
    public String toString() {
        return "Ku{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", karma=" + karma +
                '}';
    }
}