package com.example.keenan.kuky.models;

/**
 * Created by hieunguyen on 12/7/15.
 */
public class KuActionRequest {

    private int userId, kuId;

    public KuActionRequest(int userId, int kuId) {
        this.userId = userId;
        this.kuId = kuId;
    }

    public int getUserId() {
        return userId;
    }

    public int getKuId() {
        return kuId;
    }

    @Override
    public String toString() {
        return "KuActionRequest{" +
                "userId=" + userId +
                ", kuId=" + kuId +
                '}';
    }
}
