package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;

public class ToMap extends AppCompatActivity {
    private Connection connect = null;
    public static final String MAP_NAME = "Girl-Scout-Cookies.MAP_NAME";
    public static final String MAP_ID = "Girl-Scout-Cookies.MAP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_map);
        // Get the Intent that started this activity and extract the string
        String mapName = getIntent().getStringExtra(MAP_NAME);
        int mapID = getIntent().getIntExtra(MAP_ID, -1);

        Intent intent;
        if (mapName.equals("test")) {
            // Display test map
            intent = new Intent(this, TestMapsActivity.class);
        } else {
            // Display fill map
            intent = new Intent(this, FillMapActivity.class);
        }

        // Send the content of message along the intent to ToMap in extra message
        intent.putExtra(MAP_NAME, mapName);
        intent.putExtra(MAP_ID, mapID);

        startActivity(intent);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(mapName);
        connect=ConnectionHelp.connect(connect,this);
        //TO DO: get map info
        ConnectionHelp.closeConnection(connect);

    }
    public void getTextFromSQL(View view) {
        TextView tx1 = findViewById(R.id.textView3);
        TextView tx2 = findViewById(R.id.textView4);

        connect = ConnectionHelp.connect(connect, this);

        try {
            String query = "SELECT * FROM TestTable WHERE name='Bas'";
            ResultSet rs = ConnectionHelp.readFromDatabase(connect, query);
            if (rs != null) {
                while (rs.next()) {
                    tx1.setText(rs.getString(1));
                }
            }
        } catch (Exception ex) {
            Log.e("Error ", ex.getMessage());
            ex.printStackTrace();
        }
        ConnectionHelp.closeConnection(connect);
    }
}