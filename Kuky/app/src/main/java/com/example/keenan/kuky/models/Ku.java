package com.example.keenan.kuky.models;

public class Ku {

    private Integer id, karma;
    private String content;
    private Double lat, lon;
    private boolean upvoted, downvoted, favorited;

    public Ku(Integer id, String content, Integer karma, Double lat, Double lon, boolean upvoted, boolean downvoted, boolean favorited) {
        this.id = id;
        this.content = content;
        this.karma = karma;
        this.lat = lat;
        this.lon = lon;
        this.upvoted = upvoted;
        this.downvoted = downvoted;
        this.favorited = favorited;
    }

    public Integer getId() {
        return id;
    }

    public String[] getContent() {
        return content.split(";");
    }

    public Integer getKarma() {
        return karma;
    }

    public void setKarma(Integer karma) {
        this.karma = karma;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public boolean getUpvoted() {
        return upvoted;
    }

    public boolean getDownvoted() {
        return downvoted;
    }

    public boolean getFavorited() {
        return favorited;
    }

    public void setUpvoted(boolean upvoted) {
        this.upvoted = upvoted;
    }

    public void setDownvoted(boolean downvoted) {
        this.downvoted = downvoted;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    @Override
    public String toString() {
        return "Ku{" +
                "id=" + id +
                ", karma=" + karma +
                ", upvoted=" + upvoted +
                ", downvoted=" + downvoted +
                ", content='" + content + '\'' +
                '}';
    }
}