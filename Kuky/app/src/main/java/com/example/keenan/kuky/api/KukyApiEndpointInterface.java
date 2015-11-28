package com.example.keenan.kuky.api;

import retrofit.http.GET;
import retrofit.http.POST;

public interface KukyApiEndpointInterface {
    @GET("/kus/all/recent")
    void getKusNew();

    @GET("/kus/all/hot")
    void getKusHot();

    @POST("/kus/new/composed")
    void postKu();

    @POST("/kus/new/favorited")
    void favoriteKu();

    @POST("/kus/{id}/upvote")
    void upvoteKu();

    @POST("/kus/{id}/downvote")
    void downvoteKu();
}