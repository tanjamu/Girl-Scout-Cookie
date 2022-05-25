package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

public class FillMapActivity extends AppCompatActivity {
    Button btnEnterSubmit, btnFinishCancel;
    Geocoder geocoder;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_map);

        btnEnterSubmit = findViewById(R.id.enter_submit_button);
        btnFinishCancel = findViewById(R.id.finish_cancel_button);

        btnEnterSubmit.setOnClickListener(this::EnterSubmit);
        btnFinishCancel.setOnClickListener(this::FinishCancel);

        geocoder = new Geocoder(getApplicationContext());

        // Get the Intent that started this activity and extract the string
        String message = getIntent().getStringExtra("Girl-Scout-Cookies.MESSAGE");
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView5);
        textView.setText(message);
    }

    private void Enter(View view) {
        List<Address> list = new LinkedList<Address>();
        try {
            list = geocoder.getFromLocationName("Huygensgebouw", 1);
            latLng = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
        } catch (Exception e) {

        }

    }

    private void Submit(View view) {

    }

    private void Finish(View view) {

    }

    private void Cancel(View view) {

    }

    private void EnterSubmit(View view) {
        if (btnEnterSubmit.getText().equals(getString(R.string.enter))) {
            Enter(view);
            btnEnterSubmit.setText(R.string.submit);
            btnFinishCancel.setText(R.string.cancel);
        } else {
            Submit(view);
            btnEnterSubmit.setText(R.string.enter);
            btnFinishCancel.setText(R.string.finish);
        }
    }

    private void FinishCancel(View view) {
        if (btnEnterSubmit.getText().equals(getString(R.string.finish))) {
            Finish(view);
            btnEnterSubmit.setText(R.string.submit);
            btnFinishCancel.setText(R.string.cancel);
        } else {
            Cancel(view);
            btnEnterSubmit.setText(R.string.enter);
            btnFinishCancel.setText(R.string.finish);
        }
    }


}