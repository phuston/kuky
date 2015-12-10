package com.example.keenan.kuky.models;

import java.util.ArrayList;

public class KuDetailResponse {
    private Ku ku;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    public Ku getKu(){
        return ku;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
