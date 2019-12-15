package com.example.authenticate_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authenticate_retrofit.api.ServiceAPI;
import com.example.authenticate_retrofit.api.ServiceGenerator;
import com.example.authenticate_retrofit.model.Login;
import com.example.authenticate_retrofit.model.Profile;
import com.example.authenticate_retrofit.model.Student;
import com.example.authenticate_retrofit.model.User;
import com.example.authenticate_retrofit.util.SharedPreferenceConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String token;
    ServiceAPI api;
    Button btnLogin;
    EditText email,password;
    SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=findViewById(R.id.btn_login);
        email = findViewById(R.id.email);
        password= findViewById(R.id.pass);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        if(preferenceConfig.readLoginStatus()){
            loginSuccess();
        }

        btnLogin.setOnClickListener(v-> loginToServer());

    }
    private void loginToServer() {
        if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) ){
            Toast.makeText(this, "please verify all input", Toast.LENGTH_SHORT).show();
        }else{
            if(email.getText().toString().trim().equals(getResources().getString(R.string.user_name))
            && password.getText().toString().trim().equals(getResources().getString(R.string.user_password))){
                loginSuccess();
            }else{
                Toast.makeText(this, "Not Correct", Toast.LENGTH_SHORT).show();
                email.setText("");
                password.setText("");
            }
        }
    }
    public void loginSuccess(){
        String code = Base64.encodeToString("123456".getBytes(),Base64.NO_WRAP);
        String tel = Base64.encodeToString("069342618".getBytes(),Base64.NO_WRAP);
        String login_type = Base64.encodeToString("parent".getBytes(),Base64.NO_WRAP);
        String device_key = Base64.encodeToString("0".getBytes(),Base64.NO_WRAP);
        String type = Base64.encodeToString("android".getBytes(),Base64.NO_WRAP);
        String app_id = Base64.encodeToString("trendsecsolution.com.schoolnotificationappdev".getBytes(),Base64.NO_WRAP);
        api = ServiceGenerator.createService(ServiceAPI.class);
        Call<User> call = api.login(new Login(code,tel,login_type,device_key,type,app_id));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    token ="Bearer "+ response.body().getToken();
                    Profile profile = response.body().getProfile();
                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("LOGIN_TOKEN",token);
                    intent.putExtra("LOGIN_PROFILE",profile);
                    startActivity(intent);
                    preferenceConfig.writeLoginStatus(true);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
