package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences preferences;

    @Override
    //called upon creation of the activity (when the screen starts to show the activity)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
    }
    //opens the SeeMapActivity
    public void seeMap(View view) {
        Intent intent = new Intent(this, SeeMapActivity.class);
        startActivity(intent);

    }
    //opens the CreateMapActivity
    public void createMap(View view) {
        Intent intent = new Intent(this, CreateMapActivity.class);
        startActivity(intent);
    }
    //opens the SettingsActivity
    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //opens the InfoActivity
    public void help(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }
}



