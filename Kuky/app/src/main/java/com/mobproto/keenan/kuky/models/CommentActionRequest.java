package com.mobproto.keenan.kuky.models;

/**
 * Model for comment upvote/downvote requests
 */
public class CommentActionRequest {
    private int userId, kuId, commentId;

    public CommentActionRequest(int userId, int kuId, int commentId) {
        this.userId = userId;
        this.kuId = kuId;
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getKuId() {
        return kuId;
    }

    public int getCommentId() {
        return commentId;
    }

    @Override
    public String toString() {
        return "CommentActionRequest{" +
                "userId=" + userId +
                ", kuId=" + kuId +
                "}";
    }
}
