package com.mobproto.keenan.kuky.models;

/**
 * Model for ku compose requests
 */
public class KuRequest {
    public static final String KU_SORT_HOT = "hot";
    public static final String KU_SORT_RECENT = "recent";

    private String ku;
    private int userId;
    private int lat;
    private int lon;

    public KuRequest(String Ku, int User_Id, int lat, int lon) {
        this.ku = Ku;
        this.userId = User_Id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getKu() {
        return ku;
    }

    public int getUserId() {
        return userId;
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "KuRequest{" +
                "ku='" + ku + '\'' +
                ", userId=" + userId +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
