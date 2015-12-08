package com.example.keenan.kuky.models;


public class KuRequest {
    private String Ku;
    private int User_Id;
    private int lat;
    private int lon;

    public KuRequest(String Ku, int User_Id, int lat, int lon) {
        this.Ku = Ku;
        this.User_Id = User_Id;
        this.lat = lat;
        this.lon = lon;
    }
}
