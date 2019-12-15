package com.example.authenticate_retrofit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenticate_retrofit.adapter.StudentAdapter;
import com.example.authenticate_retrofit.api.ServiceAPI;
import com.example.authenticate_retrofit.api.ServiceGenerator;
import com.example.authenticate_retrofit.model.Login;
import com.example.authenticate_retrofit.model.Profile;
import com.example.authenticate_retrofit.model.Student;
import com.example.authenticate_retrofit.model.User;
import com.example.authenticate_retrofit.util.SharedPreferenceConfig;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,EasyPermissions.PermissionCallbacks {
    ServiceAPI api;
    private String token;
    private Profile profile;

    private RecyclerView recyclerView;
    private List<Student> studentList;
    private StudentAdapter adapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferenceConfig preferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout =findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        api = ServiceGenerator.createService(ServiceAPI.class);
        token =getIntent().getStringExtra("LOGIN_TOKEN");
        profile =(Profile) getIntent().getSerializableExtra("LOGIN_PROFILE");

        recyclerView =findViewById(R.id.recyclerView);


        studentList = new ArrayList<>();
        adapter=  new StudentAdapter(studentList, new StudentAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(int position) {
                Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
                        intent.putExtra("DETAIL_NAME",studentList.get(position).getName_en());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getStudent();

        setupDrawer();
    }


    private void setupDrawer() {

        navigationView= findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.profile_name);
        navImage = headerView.findViewById(R.id.profile_img);

        navUsername.setText(profile.getName());
        Toast.makeText(this, profile.getPhoto(), Toast.LENGTH_LONG).show();
//        navImage.setImageURI(Uri.parse(profile.getPhoto()));

    }
    TextView navUsername;
    ImageView navImage;
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private static final String TAG = "MainActivity";
    private void getStudent() {
        Call<ResponseBody> call = api.getStudent(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Type studentListType = new TypeToken<ArrayList<Student>>(){}.getType();
                    List<Student> students= new Gson().fromJson(response.body().string(), studentListType);
                    studentList.addAll(students);
                    adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.changeName:
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            final EditText input = new EditText(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            String old= navUsername.getText().toString();
            input.setText(old);
            dialog.setView(input);
            dialog.setIcon(R.drawable.ic_person);
            dialog.setTitle("Change Name")
                  .setPositiveButton("ok", (dialog1, which) -> {
                      navUsername.setText(input.getText().toString());

                      if(!TextUtils.isEmpty(input.getText().toString())){
                          Call<ResponseBody> call = api.changeName(token,input.getText().toString());
                          call.enqueue(new Callback<ResponseBody>() {
                              @Override
                              public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                  Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                  dialog1.dismiss();
                              }

                              @Override
                              public void onFailure(Call<ResponseBody> call, Throwable t) {
                                  Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                  dialog1.dismiss();
                              }
                          });
                      }else{
                          Toast.makeText(this, "Please Input Name", Toast.LENGTH_SHORT).show();
                      }
                  })
                  .setNegativeButton("cancel", (dialog12, which) -> dialog12.dismiss());
            dialog.show();
                break;
            case R.id.changePic:
                openExternal();
                break;
            case R.id.logout:
                preferenceConfig.writeLoginStatus(false);
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
        return true;
    }
    Uri uri;


    @AfterPermissionGranted(1)
    private void openExternal() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this,perms)){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,0);
        }else{
            EasyPermissions.requestPermissions(
                    this,
                    "we need this permission to open your gallery",
                    1,
                    perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && data != null){
            uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                navImage.setImageBitmap(bitmap);
                navImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                uploadImage(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void uploadImage(Uri uri){
        String type = Base64.encodeToString("base64".getBytes(),Base64.NO_WRAP);
        RequestBody typePart = RequestBody.create(MultipartBody.FORM,type);

        File file = new File(getRealPathImg(this,uri));
        RequestBody filePart = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part fileImg =MultipartBody.Part.createFormData("file",file.getName(),filePart);

        Call<ResponseBody> call = api.changePicture(token,fileImg,typePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String getRealPathImg(Context context,Uri uri){
        Cursor cursor=null;
        try{
            String[] pro = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri,pro,null,null);
            int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(index);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    }
}
