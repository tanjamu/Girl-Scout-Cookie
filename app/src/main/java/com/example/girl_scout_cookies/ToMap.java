package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ToMap extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "Girl-Scout-Cookies.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_map);
        // Get the Intent that started this activity and extract the string
        String message = getIntent().getStringExtra("Girl-Scout-Cookies.MESSAGE");

        Intent intent;
        if (message.equals("test")) {
            // Display test map
            intent = new Intent(this, TestMapsActivity.class);
        } else {
            // Display fill map
            intent = new Intent(this, FillMapActivity.class);
        }

        // Send the content of message along the intent to ToMap in extra message
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }
}