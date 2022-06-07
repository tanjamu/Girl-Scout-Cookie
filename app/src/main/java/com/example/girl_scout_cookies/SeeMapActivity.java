package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;

public class SeeMapActivity extends AppCompatActivity {
    public static final String MAP_NAME = "Girl-Scout-Cookies.MAP_NAME";
    public static final String MAP_ID = "Girl-Scout-Cookies.MAP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_map);
    }

    public void toMap(View view) {
        // Get the content of text field of editTextMapCode
        EditText editText = (EditText) findViewById(R.id.editTextMapCode);
        String mapName = editText.getText().toString();
        Connection connect = ConnectionHelp.openConnection(this);

        if (SQLHelp.mapExists(mapName, connect)) {
            int mapID = SQLHelp.getMapID(mapName, connect);
            ConnectionHelp.closeConnection(connect);

            Intent intent = new Intent(this, FillMapActivity.class);
            // Send the content of message along the intent to ToMap in extra message
            intent.putExtra(MAP_NAME, mapName);
            intent.putExtra(MAP_ID, mapID);
            startActivity(intent);
        } else {
            ConnectionHelp.closeConnection(connect);
            TextView t = findViewById(R.id.textView5);
            t.setText(R.string.not_existing_map);
        }
    }
}