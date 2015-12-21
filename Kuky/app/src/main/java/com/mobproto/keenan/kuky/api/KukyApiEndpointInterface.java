package com.mobproto.keenan.kuky.api;

import com.mobproto.keenan.kuky.models.CommentActionRequest;
import com.mobproto.keenan.kuky.models.CommentActionResponse;
import com.mobproto.keenan.kuky.models.CommentComposeRequest;
import com.mobproto.keenan.kuky.models.CommentComposeResponse;
import com.mobproto.keenan.kuky.models.KuActionRequest;
import com.mobproto.keenan.kuky.models.KuActionResponse;
import com.mobproto.keenan.kuky.models.KuComposeResponse;
import com.mobproto.keenan.kuky.models.KuDetailResponse;
import com.mobproto.keenan.kuky.models.KuListResponse;
import com.mobproto.keenan.kuky.models.KuRequest;
import com.mobproto.keenan.kuky.models.User;
import com.mobproto.keenan.kuky.models.UserApiKeyResponse;
import com.mobproto.keenan.kuky.models.UserRequest;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface KukyApiEndpointInterface {

    /**
     * Get all Ku Details
     * @param kuId
     * @param userId
     * @return Observable emitting KuDetailResponse objects
     */
    @GET("/kus/{kuId}/{userId}")
    Observable<KuDetailResponse> getKuDetail(
            @Path("kuId") String kuId,
            @Path("userId") String userId
    );

    /**
     * Get all Hot or Recent Kus
     * @param sort Ku sort (hot or recent)
     * @param userId
     * @return Observable emitting KuListResponse
     */
    @GET("/kus/all/{sort}/{userId}")
    Observable<KuListResponse> getKus(
            @Path("sort") String sort,
            @Path("userId") String userId
    );

    /**
     * Post a new Ku
     * @param body the Ku Body
     * @return Observable emitting KuComposeResponse
     */
    @POST("/kus/compose")
    Observable<KuComposeResponse> postKu(
            @Body KuRequest body
    );

    /**
     * Favorite a Ku
     * @param body the Favorite Body KuActionRequest
     * @return Observable emitting KuActionResponse
     */
    @POST("/kus/favorite")
    Observable<KuActionResponse> favoriteKu(
            @Body KuActionRequest body
    );

    /**
     * Upvote a Ku
     * @param body the upvote request Body KuActionRequest
     * @return Observable emitting KuActionRequest
     */
    @POST("/kus/upvote")
    Observable<KuActionResponse> upvoteKu(
            @Body KuActionRequest body
    );

    /**
     * Downvote a Ku
     * @param body the upvote request Body KuActionRequest
     * @return Observable emitting KuActionRequest
     */
    @POST("/kus/downvote")
    Observable<KuActionResponse> downvoteKu(
            @Body KuActionRequest body
    );

    /**
     * Login endpoint
     * @param body UserRequest body
     * @return UserApiKeyResponse with API request
     */
    @POST("/users/login")
    Observable<UserApiKeyResponse> login(
            @Body UserRequest body
    );

    /**
     * Register endpoint
     * @param body UserRequest body
     * @return UserApiKeyResponse with API request
     */
    @POST("/users/register")
    Observable<UserApiKeyResponse> register(
            @Body UserRequest body
    );

    /**
     * Get the user's information
     * @param id UserID
     * @return User object
     */
    @GET("/users/{uname}")
    Observable<User> getUser(
            @Path("uname") String id
    );

    /**
     * Upvote a Comment
     * @param body the upvote request Body CommentActionRequest
     * @return Observable emitting CommentActionResponse
     */
    @POST("/comments/upvote")
    Observable<CommentActionResponse> upvoteComment(
            @Body CommentActionRequest body
    );

    /**
     * Downvote a Comment
     * @param body the downvote request Body CommentActionRequest
     * @return Observable emitting CommentActionResponse
     */
    @POST("/comments/downvote")
    Observable<CommentActionResponse> downvoteComment(
            @Body CommentActionRequest body
    );

    /**
     * Compose a comment
     * @param body CommentComposeRequest representing the comment
     * @return Observable emitting CommentComposeResponse objects
     */
    @POST("/comments/compose")
    Observable<CommentComposeResponse> postComment (
            @Body CommentComposeRequest body
    );

}