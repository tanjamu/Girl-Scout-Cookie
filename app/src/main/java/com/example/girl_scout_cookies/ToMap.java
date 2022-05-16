package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ToMap extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_map);
        // Get the Intent that started this activity and extract the string
        String message = getIntent().getStringExtra("Girl-Scout-Cookies.MESSAGE");

        if (message.equals("TestMap")) {
            // Display test map
            Intent intent = new Intent(this, TestMapsActivity.class);
            startActivity(intent);
        }

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }
}