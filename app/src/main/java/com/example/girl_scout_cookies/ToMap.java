package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ToMap extends AppCompatActivity {
    Connection connect = null;
    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_map);
        // Get the Intent that started this activity and extract the string
        android.content.Intent intent = getIntent();
        String message = getIntent().getStringExtra("Girl-Scout-Cookies.MESSAGE");
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
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
        connect = ConnectionHelp.closeConnection(connect);
    }
}