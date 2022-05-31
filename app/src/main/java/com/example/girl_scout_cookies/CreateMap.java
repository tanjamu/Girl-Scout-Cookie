package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMap extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_map);
    }

    public void toMap(View view) {
        //get the content of text field of editTextMapCode
        EditText editText = (EditText) findViewById(R.id.editTextNewCode);
        String mapName = editText.getText().toString();

        // TODO: Query database: if the name exists, cancel creating the map
        // TODO: Otherwise, create the entry into the database
        int mapID = 0;

        Intent intent = new Intent(this, ToMap.class);

        //send the map info along the intent to ToMap in extra message
        intent.putExtra(ToMap.MAP_NAME, mapName);
        intent.putExtra(ToMap.MAP_ID, mapID);
        startActivity(intent);
    }
}