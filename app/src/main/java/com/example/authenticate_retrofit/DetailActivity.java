package com.example.authenticate_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView=findViewById(R.id.myname);
        textView.setText(getIntent().getStringExtra("DETAIL_NAME"));
    }
    TextView textView;
}
