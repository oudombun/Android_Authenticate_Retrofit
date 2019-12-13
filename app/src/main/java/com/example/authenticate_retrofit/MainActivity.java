package com.example.authenticate_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.authenticate_retrofit.api.ServiceAPI;
import com.example.authenticate_retrofit.api.ServiceGenerator;
import com.example.authenticate_retrofit.model.Login;
import com.example.authenticate_retrofit.model.Profile;
import com.example.authenticate_retrofit.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button btnLogin,btnGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGet= findViewById(R.id.btn_get);
        btnLogin=findViewById(R.id.btn_login);
        api = ServiceGenerator.createService(ServiceAPI.class);

        btnLogin.setOnClickListener(v-> loginToServer());
        btnGet.setOnClickListener(v->getStudent());
    }

    private static final String TAG = "MainActivity";
    private void getStudent() {
        Call<ResponseBody> call = api.getStudent(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type profileListType = new TypeToken<ArrayList<Profile>>(){}.getType();
                    List<Profile> profiles= new Gson().fromJson(response.body().string(), profileListType);
                    Toast.makeText(MainActivity.this, "list:"+profiles.size(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String token;
    ServiceAPI api;
    private void loginToServer() {

        String code = Base64.encodeToString("123456".getBytes(),Base64.NO_WRAP);
        String tel = Base64.encodeToString("069342618".getBytes(),Base64.NO_WRAP);
        String login_type = Base64.encodeToString("parent".getBytes(),Base64.NO_WRAP);
        String device_key = Base64.encodeToString("0".getBytes(),Base64.NO_WRAP);
        String type = Base64.encodeToString("android".getBytes(),Base64.NO_WRAP);
        String app_id = Base64.encodeToString("trendsecsolution.com.schoolnotificationappdev".getBytes(),Base64.NO_WRAP);
        Call<User> call = api.login(new Login(code,tel,login_type,device_key,type,app_id));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    token ="Bearer "+ response.body().getToken();
                    Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
