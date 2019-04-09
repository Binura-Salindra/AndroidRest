package com.example.androidrest.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidrest.R;
import com.example.androidrest.controller.CustomerController;
import com.example.androidrest.model.Customer;
import com.example.androidrest.model.network.StandardResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button btnSaveCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        setListeners();
    }

    private void initComponents() {
        btnSaveCustomer = findViewById(R.id.saveCustomer);
    }

    private void setListeners() {
        btnSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCustomerDetailsToServer();
            }
        });
    }

    private void sendCustomerDetailsToServer() {
        new CustomerController().saveCustomerDetails(new Customer(),getCustomerSaveCallBack());
    }

    private Callback<StandardResponse> getCustomerSaveCallBack() {
        Callback<StandardResponse> callback = new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                //check network error or not
                if(response.isSuccessful()){
                    StandardResponse responseBody = response.body();
                    if(responseBody.isSuccess()){
                        Toast.makeText(context,"Customer Details Saved..!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,"Failed to save Customer",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Toast.makeText(context,"Failed to save Customer",Toast.LENGTH_LONG).show();
            }
        };
        return callback;
    }
}
