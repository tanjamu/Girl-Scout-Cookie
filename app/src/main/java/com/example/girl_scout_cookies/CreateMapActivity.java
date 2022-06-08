package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;

public class CreateMapActivity extends AppCompatActivity {
    Connection connect = null;
    public static final String MAP_NAME = "Girl-Scout-Cookies.MAP_NAME";
    public static final String MAP_ID = "Girl-Scout-Cookies.MAP_ID";

    @Override
    //called upon creation of the activity (when the screen starts to show the activity)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_map);
    }
    //open the FillMapActivity and show the map if the map code does not already exists if it exists then shows a message
    public void toMap(View view) {
        //get the content of text field of editTextMapCode
        EditText editText = (EditText) findViewById(R.id.editTextNewCode);
        String mapName = editText.getText().toString();

        connect = ConnectionHelp.openConnection(this);
        if (SQLHelp.mapExists(mapName, connect)) {
            TextView t = findViewById(R.id.textView2);
            t.setText(R.string.error_existing_code);
            ConnectionHelp.closeConnection(connect);
        } else {
            SQLHelp.createMap(mapName, connect);
            int mapID = SQLHelp.getMapID(mapName, connect);
            ConnectionHelp.closeConnection(connect);

            Intent intent = new Intent(this, FillMapActivity.class);

            // Send the content of message along the intent to ToMap in extra message
            intent.putExtra(MAP_NAME, mapName);
            intent.putExtra(MAP_ID, mapID);

            startActivity(intent);
        }
    }
}