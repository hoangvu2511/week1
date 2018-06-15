package com.example.vuhoang.flicks.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static InterfaceApi interfaceApi;

    public static InterfaceApi getApi(){
        if (interfaceApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(InterfaceApi.Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            interfaceApi = retrofit.create  (InterfaceApi.class);
        }
        return interfaceApi;
    }
}
