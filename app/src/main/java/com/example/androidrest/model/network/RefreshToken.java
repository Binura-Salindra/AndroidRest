package com.example.androidrest.model.network;

public class RefreshToken {

    private String userName;
    private String refresh_token;

    public RefreshToken(String userName, String refresh_token) {
        this.userName = userName;
        this.refresh_token = refresh_token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String access_token) {
        this.refresh_token = access_token;
    }
}
