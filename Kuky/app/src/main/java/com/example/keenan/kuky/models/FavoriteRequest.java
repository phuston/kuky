package com.example.keenan.kuky.models;

public class FavoriteRequest {

    private int User_id;
    private int Ku_id;

    public FavoriteRequest(int User_id, int Ku_id) {
        this.User_id = User_id;
        this.Ku_id = Ku_id;
    }
}
