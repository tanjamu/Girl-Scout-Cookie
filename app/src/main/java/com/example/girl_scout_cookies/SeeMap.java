package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;

public class SeeMap extends AppCompatActivity {
//    public static final String EXTRA_MESSAGE = "Girl-Scout-Cookies.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_map);
    }

    public void toMap(View view) {
        Intent intent = new Intent(this, ToMap.class);
        // Get the content of text field of editTextMapCode
        EditText editText = (EditText) findViewById(R.id.editTextMapCode);
        String mapName = editText.getText().toString();
        Connection c = ConnectionHelp.openConnection(this);
        if (GetMap.mapExists(mapName, c)) {
            // Send the content of message along the intent to ToMap in extra message
            intent.putExtra(ToMap.MAP_NAME, mapName);
            startActivity(intent);
        } else {
            TextView t = findViewById(R.id.textView5);
            t.setText(R.string.not_existing_map);
        }
        ConnectionHelp.closeConnection(c);
    }
}