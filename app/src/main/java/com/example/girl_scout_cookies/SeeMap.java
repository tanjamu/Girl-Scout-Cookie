package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SeeMap extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "Girl-Scout-Cookies.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_map);
    }

    public void toMap(View view) {
        Intent intent = new Intent(this, ToMap.class);
        //get the content of text fiel of editTextMapCode
        EditText editText = (EditText) findViewById(R.id.editTextMapCode);
        String message = editText.getText().toString();
        //send the content of message allong the intent to ToMap in extra message
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}