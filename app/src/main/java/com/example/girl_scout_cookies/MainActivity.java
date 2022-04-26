package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void seeMap(View view) {
        Intent intent = new Intent(this, SeeMap.class);
        startActivity(intent);
    }

    public void createMap(View view) {
        Intent intent = new Intent(this, CreateMap.class);
        startActivity(intent);
    }

}



