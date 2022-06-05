package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.Connection;

public class Settings extends AppCompatActivity {
    Connection connect = null;

    @Override
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

    public void createTables(View view) {
        connect = ConnectionHelp.openConnection(this);
        GetMap.createTable("Main(addressID INT, mapID INT, colorID INT)", connect);
        GetMap.createTable("Address(addressID INT, latitude FLOAT(53), longitude FLOAT(53))", connect);
        GetMap.createTable("Color(colorID INT, color BIGINT)", connect);
        GetMap.createTable("Map(mapID INT, mapCode VARCHAR(255))", connect);
        ConnectionHelp.closeConnection(connect);
    }
}