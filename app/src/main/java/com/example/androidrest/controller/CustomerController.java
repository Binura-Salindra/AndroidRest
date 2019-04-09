package com.example.androidrest.controller;

import com.example.androidrest.model.Customer;
import com.example.androidrest.model.network.StandardResponse;

import retrofit2.Callback;

public class CustomerController extends AbstractController {

    public void saveCustomerDetails(Customer customer, Callback<StandardResponse> callback){
        myRestAPI.saveCustomerDetails(customer);
    }
}
