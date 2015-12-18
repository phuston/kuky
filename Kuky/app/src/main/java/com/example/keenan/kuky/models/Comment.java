package com.example.keenan.kuky.models;


public class Comment {

    private Integer id;
    private String content;
    private Integer karma;
    private boolean isOp, upvoted, downvoted;

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Integer getKudos() {
        return karma;
    }

    public boolean isUpvoted() {
        return upvoted;
    }

    public void setUpvoted(boolean upvoted) {
        this.upvoted = upvoted;
    }

    public boolean isDownvoted() {
        return downvoted;
    }

    public void setDownvoted(boolean downvoted) {
        this.downvoted = downvoted;
    }

    public void setKudos(int kudos) {
        this.karma = kudos;
    }

    public boolean isOp() {
        return isOp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", kudos=" + karma +
                ", isOp=" + isOp +
                ", upvoted=" + upvoted +
                ", downvoted=" + downvoted +
                '}';
    }
}
