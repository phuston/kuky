package com.example.keenan.kuky.models;


public class KuRequest {
    public static final String KU_SORT_HOT = "hot";
    public static final String KU_SORT_RECENT = "recent";

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
