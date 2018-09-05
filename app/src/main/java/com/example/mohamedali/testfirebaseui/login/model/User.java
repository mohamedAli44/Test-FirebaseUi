package com.example.mohamedali.testfirebaseui.login.model;

/**
 * Created by Mohamed Ali on 03/09/2018.
 */

public class User {
    public static final String USERNAME = "username";
    public static final String PHOTO_URL = "photoUrl";
    public static final String UID = "uid";

    public static final String USERS_PHOTO = "users_photo";


    private String username;
    private String photoUrl;
    private String uid;

    public User(String username, String photoUrl, String uid) {
        this.username = username;
        this.photoUrl = photoUrl;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUid() {
        return uid;
    }
}
