package com.example.androidrest.constant;

public class ServerURL {

    public static final String BASE_SERVER_URL = " ";
    public static final String USER_ENDPOINT = "Users";
    public static final String TOKEN_ENDPOINT = "/token";
    public static final String REFRESH_TOKEN_URL = BASE_SERVER_URL + USER_ENDPOINT + "/%s" + TOKEN_ENDPOINT;
    public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json; charset=utf-8";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

}
