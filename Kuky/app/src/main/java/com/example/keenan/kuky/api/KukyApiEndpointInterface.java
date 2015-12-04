package com.example.keenan.kuky.api;

import com.example.keenan.kuky.models.KuComposeResponse;
import com.example.keenan.kuky.models.KuRequest;
import com.example.keenan.kuky.models.KuResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface KukyApiEndpointInterface {

    // Kus Endpoints

    @GET("/kus/all/recent")
    Observable<KuResponse> getKusNew();

    @GET("/kus/all/hot")
    Observable<KuResponse> getKusHot();

    @POST("/kus/compose")
    Observable<KuComposeResponse> postKu(@Body KuRequest body);

    @POST("/kus/new/favorited")
    void favoriteKu();
    Observable<KuFavoriteResponse> postFavKu(@Body )

    @POST("/kus/{id}/upvote")
    void upvoteKu();

    @POST("/kus/{id}/downvote")
    void downvoteKu();

    // Users Endpoints
    @POST("/users/login/{userid}/{pw}")
    void login(
        @Path("userid") String userid,
        @Path("pw") String pw);

    @POST("/users/register/{uname}/{pw}")
    void register();

    @GET("/users/{uname}")
    void getUser(@Path("uname") String id);


    // Comments Endpoints

}