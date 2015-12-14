package com.example.keenan.kuky.api;

import com.example.keenan.kuky.models.CommentActionRequest;
import com.example.keenan.kuky.models.CommentActionResponse;
import com.example.keenan.kuky.models.KuActionRequest;
import com.example.keenan.kuky.models.KuActionResponse;
import com.example.keenan.kuky.models.KuComposeResponse;
import com.example.keenan.kuky.models.KuDetailResponse;
import com.example.keenan.kuky.models.KuRequest;
import com.example.keenan.kuky.models.KuListResponse;
import com.example.keenan.kuky.models.UserApiKeyResponse;
import com.example.keenan.kuky.models.UserProfileResponse;
import com.example.keenan.kuky.models.UserRequest;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface KukyApiEndpointInterface {

    // Kus Endpoints

    @GET("/kus/{id}")
    Observable<KuDetailResponse> getKuDetail(
            @Path("id") String id
    );

    @GET("/kus/all/{sort}")
    Observable<KuListResponse> getKus(
            @Path("sort") String sort
    );

    @POST("/kus/compose")
    Observable<KuComposeResponse> postKu(
            @Body KuRequest body
    );

    @POST("/kus/favorite")
    Observable<KuActionResponse> favoriteKu(
            @Body KuActionRequest body
    );

    @POST("/kus/upvote")
    Observable<KuActionResponse> upvoteKu(
            @Body KuActionRequest body
    );

    @POST("/kus/downvote")
    Observable<KuActionResponse> downvoteKu(
            @Body KuActionRequest body
    );

    // Users Endpoints
    @POST("/users/login")
    Observable<UserApiKeyResponse> login(
            @Body UserRequest body
    );

    @POST("/users/register")
    Observable<UserApiKeyResponse> register(
            @Body UserRequest body
    );

    @GET("/users/{uname}")
    Observable<UserProfileResponse> getUser(
            @Path("uname") String id
    );

    // Comments Endpoints
//    @GET("/comments/{id}")
//    Observable<CommentListResponse> getComments(
//            @Path("id") String id
//    );

    @POST("/comments/upvote")
    Observable<CommentActionResponse> upvoteComment(
            @Body CommentActionRequest body
    );

    @POST("/comments/downvote")
    Observable<CommentActionResponse> downvoteComment(
            @Body CommentActionRequest body
    );

}