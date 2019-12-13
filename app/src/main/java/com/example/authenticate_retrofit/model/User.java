package com.example.authenticate_retrofit.model;

public class User {
    private Profile profile;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(Profile profile, String token) {
        this.profile = profile;
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "profile=" + profile +
                '}';
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User(Profile profile) {
        this.profile = profile;
    }
}
