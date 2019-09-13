package com.itgenius.ministock.api;

import com.itgenius.ministock.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RestAPI {

    //  ฟังก์ชัน login
    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> checkLogin(
            @Field("username") String username,
            @Field("password") String password
    );

}
