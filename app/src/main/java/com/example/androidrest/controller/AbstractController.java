package com.example.androidrest.controller;

import android.util.Log;

import com.example.androidrest.constant.ServerURL;
import com.example.androidrest.rest.MyRestAPI;
import com.example.androidrest.rest.OAuth2Authenticator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AbstractController {

    private static final int NETWORK_CALL_RETRY_COUNT = 3;
    Gson gson;
    Retrofit retrofit;
    MyRestAPI myRestAPI;
    HttpLoggingInterceptor loggingInterceptor;
    Interceptor retryInterceptor;
    Interceptor oauthInterceptor;
    Authenticator oAuthAuthenticator;

    public AbstractController() {
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        retryInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int tryCount = 0;
                while (!response.isSuccessful() && tryCount < NETWORK_CALL_RETRY_COUNT) {
                    Log.d("intercept", "Request is not successful - " + tryCount);
                    tryCount++;
                    response = chain.proceed(request);
                }
                return response;
            }
        };
        oauthInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                if (original.header(ServerURL.HEADER_AUTHORIZATION) == null
                        || original.header(ServerURL.HEADER_AUTHORIZATION).isEmpty()) {
//                    String token = SharedPreferencesHandler.getAccessToken(NenasaApplication.context);
                    String token = "";
                    if (token != null) {
                        Request request = original.newBuilder()
                                .header(ServerURL.HEADER_AUTHORIZATION,ServerURL.BEARER+token)
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                }
                return chain.proceed(original);
            }
        };
        gson = new GsonBuilder()
                .setLenient()
                .create();
        oAuthAuthenticator = new OAuth2Authenticator();
        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .authenticator(oAuthAuthenticator)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(retryInterceptor)
                .addInterceptor(oauthInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerURL.BASE_SERVER_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myRestAPI = retrofit.create(MyRestAPI.class);
    }
}
