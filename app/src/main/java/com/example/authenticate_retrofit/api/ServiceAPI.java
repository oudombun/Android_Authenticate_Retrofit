package com.example.authenticate_retrofit.api;

import com.example.authenticate_retrofit.model.Login;
import com.example.authenticate_retrofit.model.User;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceAPI {
    @POST("api/login")
    Call<User> login(@Body Login login);


    @GET("api/load_student")
    Call<ResponseBody> getStudent(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/update_mobile_user_name")
    Call<ResponseBody> changeName(@Header("Authorization") String token,@Field("name") String name);

    @Multipart
    @POST("api/update/user-photo")
    Call<ResponseBody> changePicture(@Header("Authorization") String token,
                                     @Part MultipartBody.Part file,
                                     @Part("type") RequestBody type);
}
