package com.example.keenan.kuky.models;

/**
 * Created by hieunguyen on 12/6/15.
 */
public class User {

    private int id;
    private String username;
    private int score;
    private double radiusLimit;

    public User(int id, String username, int score, double radiusLimit) {
        this.id = id;
        this.username = username;
        this.score = score;
        this.radiusLimit = radiusLimit;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", radiusLimit=" + radiusLimit +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public double getRadiusLimit() {
        return radiusLimit;
    }
}
