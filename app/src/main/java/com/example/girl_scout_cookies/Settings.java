package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

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
    }
}