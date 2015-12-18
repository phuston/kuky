package com.example.keenan.kuky.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Model for response from user login/register before API keys were implemented
 */
public class UserProfileResponse {
    @SerializedName("id")
    private int userId;

    @SerializedName("username")
    private String username;

    @SerializedName("score")
    private int score;

    @SerializedName("radiusLimit")
    private double radiusLimit;

    @SerializedName("composedKus")
    private ArrayList<Ku> composedKus;

    @SerializedName("favoritedKus")
    private ArrayList<Ku> favoritedKus;

    public UserProfileResponse(int id, String uname, int score, double radiusLimit, ArrayList<Ku> favKus, ArrayList<Ku> compKus) {
        this.userId = id;
        this.username = uname;
        this.score = score;
        this.radiusLimit = radiusLimit;
        this.favoritedKus = favKus;
        this.composedKus = compKus;
    }

    public int getUserId() {
        return userId;
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

    public ArrayList<Ku> getComposedKus() {
        return composedKus;
    }

    public ArrayList<Ku> getFavoritedKus() {
        return favoritedKus;
    }

    @Override
    public String toString() {
        return "UserProfileResponse{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", radiusLimit=" + radiusLimit +
                ", composedKus=" + composedKus +
                ", favoritedKus=" + favoritedKus +
                '}';
    }
}
