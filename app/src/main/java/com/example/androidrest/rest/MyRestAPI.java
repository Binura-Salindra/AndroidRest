package com.example.androidrest.rest;

import com.example.androidrest.model.Customer;
import com.example.androidrest.model.network.StandardResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyRestAPI {

    @POST("api/v1/customers")
    Call<StandardResponse> saveCustomerDetails(@Body Customer customer);
}
