package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FillMapActivity extends AppCompatActivity {
    Button btnEnterSubmit, btnFinishCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_map);

        btnEnterSubmit = findViewById(R.id.enter_submit_button);
        btnFinishCancel = findViewById(R.id.finish_cancel_button);

        btnEnterSubmit.setOnClickListener(this::EnterSubmit);
        btnFinishCancel.setOnClickListener(this::FinishCancel);

        // Get the Intent that started this activity and extract the string
        String message = getIntent().getStringExtra("Girl-Scout-Cookies.MESSAGE");
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView5);
        textView.setText(message);
    }

    private void Enter(View view) {
        //use geocoder to convert entered address to location, and display location.
        // if geocoder fails, don't go to the second menu.
    }

    private void Submit(View view) {
        //choose a color and save.
    }

    private void Finish(View view) {
        //save changes and exit.
    }

    private void Cancel(View view) {
        //exit without saving changes.
    }

    private void EnterSubmit(View view) {
        if (btnEnterSubmit.getText().equals(R.string.enter)) {
            Enter(view);
            btnEnterSubmit.setText(R.string.submit);
        } else {
            Submit(view);
            btnEnterSubmit.setText(R.string.enter);
        }
    }

    private void FinishCancel(View view) {
        if (btnEnterSubmit.getText().equals(R.string.finish)) {
            Finish(view);
            btnFinishCancel.setText(R.string.cancel);
        } else {
            Cancel(view);
            btnFinishCancel.setText(R.string.finish);
        }
    }

}