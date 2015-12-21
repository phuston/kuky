package com.mobproto.keenan.kuky.models;

/**
 * Model for logging in and registering a user
 */
public class UserRequest {
    private String username;
    private String password;

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
