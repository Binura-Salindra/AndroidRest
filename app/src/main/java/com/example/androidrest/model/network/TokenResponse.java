package com.example.androidrest.model.network;

public class TokenResponse {
    private boolean success;
    private boolean userAvailability;
    private boolean auth;
    private String access_token;
    private String refresh_token;
    private String msg;

    public TokenResponse() {
    }

    public TokenResponse(boolean success, boolean userAvailability, boolean auth, String access_token, String refresh_token, String msg) {
        this.success = success;
        this.userAvailability = userAvailability;
        this.auth = auth;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isUserAvailability() {
        return userAvailability;
    }

    public void setUserAvailability(boolean userAvailability) {
        this.userAvailability = userAvailability;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
