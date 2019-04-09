package com.example.androidrest.rest;

import android.support.annotation.Nullable;

import com.example.androidrest.MyApplication;
import com.example.androidrest.constant.ServerURL;
import com.example.androidrest.model.network.RefreshToken;
import com.example.androidrest.model.network.TokenResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class OAuth2Authenticator implements Authenticator {

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        // Refresh access_token using Customer synchronous api request
        String newAccessToken = getNewToken();
        if (newAccessToken == null || newAccessToken.equals(response.request().header(ServerURL.HEADER_AUTHORIZATION))) {
//            SharedPreferencesHandler.setLoginStatus(MyApplication.context, false);
            return null; // If we already failed with these credentials, don't retry.
        }
        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header(ServerURL.HEADER_AUTHORIZATION, newAccessToken)
//                .header(ServerURL.HEADER_AUTHORIZATION, ServerURL.BEARER+newAccessToken)
                .build();

    }

    private String getNewToken(){
        String token = null;
        final OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        RefreshToken credentials = new RefreshToken(null, null);
        // set refresh token from sharedPreference
//        credentials = SharedPreferencesHandler.getCredentials(MyApplication.context);
        if (credentials != null) {
            String requestBody = gson.toJson(credentials);
            Request request = new Request.Builder()
                    .url(String.format(ServerURL.REFRESH_TOKEN_URL, credentials.getUserName()))
                    .post(RequestBody.create(MediaType.parse(ServerURL.MEDIA_TYPE_APPLICATION_JSON), requestBody))
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                TokenResponse tokenResponse = new Gson().fromJson(response.body().string(), TokenResponse.class);
                if (tokenResponse.isSuccess() && tokenResponse.isAuth() && tokenResponse.isUserAvailability()) {
                    token = tokenResponse.getAccess_token();
                    // save tokens on sharedPreference
//                    SharedPreferencesHandler.saveAccessToken(MyApplication.context, token);
//                    SharedPreferencesHandler.saveRefreshToken(MyApplication.context, tokenResponse.getRefresh_token());
                } else {
                    //if token not valid set login statis false
//                    SharedPreferencesHandler.setLoginStatus(MyApplication.context, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        return token;
    }
}
