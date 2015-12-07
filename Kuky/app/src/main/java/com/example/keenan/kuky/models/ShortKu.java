package com.example.keenan.kuky.models;

/**
 * Created by hieunguyen on 12/6/15.
 */
public class ShortKu {

    private int id;
    private String content;
    private int karma;
    private double lat;
    private double lon;

    public ShortKu(int id, String content, int karma, double lat, double lon) {
        this.id = id;
        this.content = content;
        this.karma = karma;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "ShortKu{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", karma=" + karma +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getKarma() {
        return karma;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
