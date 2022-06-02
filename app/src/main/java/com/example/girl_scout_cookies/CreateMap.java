package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;

public class CreateMap extends AppCompatActivity {
    Connection connect = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_map);
    }

    public void toMap(View view) {
        //get the content of text field of editTextMapCode
        EditText editText = (EditText) findViewById(R.id.editTextNewCode);
        String mapName = editText.getText().toString();

        connect = ConnectionHelp.connect(connect, this);
        if (GetMap.mapExists(mapName, connect)) {
            TextView t = findViewById(R.id.textView2);
            t.setText(R.string.error_existing_code);
        } else {
            int mapID = 0;

            Intent intent = new Intent(this, ToMap.class);

            //send the map info along the intent to ToMap in extra message
            intent.putExtra(ToMap.MAP_NAME, mapName);
            intent.putExtra(ToMap.MAP_ID, mapID);
            startActivity(intent);
        }


    }
}