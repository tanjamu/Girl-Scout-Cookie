package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.Connection;

public class SettingsActivity extends AppCompatActivity {
    Connection connect = null;

    @Override
    //called upon creation of the activity(when the screen starts to show the activity)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText ip = findViewById(R.id.editTextIP);
        EditText db = findViewById(R.id.editTextDB);
        EditText user = findViewById(R.id.editTextUser);
        EditText pass = findViewById(R.id.editTextPass);

        user.setText(MainActivity.preferences.getString("user", "def_user"));
        pass.setText(MainActivity.preferences.getString("password", "def_password"));
        db.setText(MainActivity.preferences.getString("database", "def_database"));
        ip.setText(MainActivity.preferences.getString("ip", "def_ip"));
    }
    //stores the settings as they are currently entered in the fields in the preferences
    public void updateSettings(View view) {
        EditText editTextIp = (EditText) findViewById(R.id.editTextIP);
        String ip = editTextIp.getText().toString();
        EditText editTextDb = (EditText) findViewById(R.id.editTextDB);
        String db = editTextDb.getText().toString();
        EditText editTextUser = (EditText) findViewById(R.id.editTextUser);
        String user = editTextUser.getText().toString();
        EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        String pass = editTextPass.getText().toString();
        PreferencesHelp.setUrl(user, pass, db, ip);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //creates tables in the database corresponding to the data in the preferences.
    public void createTables(View view) {
        connect = ConnectionHelp.openConnection(this);
        SQLHelp.dropTable("Main", connect);
        SQLHelp.dropTable("Address", connect);
        SQLHelp.dropTable("Color", connect);
        SQLHelp.dropTable("Map", connect);
        SQLHelp.createTable("Main(addressID INT, mapID INT, colorID INT)", connect);
        SQLHelp.createTable("Address(addressID INT IDENTITY(1,1) PRIMARY KEY, latitude FLOAT(53), longitude FLOAT(53))", connect);
        SQLHelp.createTable("Color(colorID INT IDENTITY(1,1) PRIMARY KEY, color BIGINT)", connect);
        SQLHelp.createTable("Map(mapID INT IDENTITY(1,1) PRIMARY KEY, Name VARCHAR(255))", connect);

        for (int color : MyColor.getColors()) {
            SQLHelp.addColor(color, connect);
        }

        ConnectionHelp.closeConnection(connect);
    }
}