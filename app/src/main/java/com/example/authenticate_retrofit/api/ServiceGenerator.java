package com.example.authenticate_retrofit.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static String BASE_URL="http://pre.schnotify.info/";


    private static HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okhttpbuilder =  new OkHttpClient.Builder().addInterceptor(interceptor);

    private static Retrofit.Builder builder = new Retrofit.Builder()
                                            .baseUrl(BASE_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .client(okhttpbuilder.build());
    public static <S> S createService(Class<S> service){


        return builder.build().create(service);
    }
}
