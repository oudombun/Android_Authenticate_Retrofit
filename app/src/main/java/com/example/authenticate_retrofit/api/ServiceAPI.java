package com.example.authenticate_retrofit.api;

import com.example.authenticate_retrofit.model.Login;
import com.example.authenticate_retrofit.model.User;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ServiceAPI {
    @POST("api/login")
    Call<User> login(@Body Login login);


    @GET("api/load_student")
    Call<ResponseBody> getStudent(@Header("Authorization") String token);

}
