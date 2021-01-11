package com.example.eatit;

public class User {
    private  int userId;
    private String fullNmae;
    private String email;
    private String userStatus;
    private String authToken;

    public User() {}

    public User(int userId, String fullNmae, String email, String userStatus, String authToken) {
        this.userId = userId;
        this.fullNmae = fullNmae;
        this.email = email;
        this.userStatus = userStatus;
        this.authToken = authToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullNmae() {
        return fullNmae;
    }

    public void setFullNmae(String fullNmae) {
        this.fullNmae = fullNmae;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
















