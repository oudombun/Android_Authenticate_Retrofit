package com.example.authenticate_retrofit.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.authenticate_retrofit.R;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context con) {
        this.context = con;
        sharedPreferences = context.getSharedPreferences(
                context.getResources().getString(R.string.login_preference),
                context.MODE_PRIVATE);
    }
    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference),status);
        editor.commit();
    }
    public boolean readLoginStatus(){
        boolean status;
        status = sharedPreferences.getBoolean(
                context.getResources().getString(R.string.login_status_preference),false);
        return status;
    }
}
