package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FillMapActivity extends AppCompatActivity {
    Button btnEnterSubmit, btnFinishCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_map);

        btnEnterSubmit = (Button) findViewById(R.id.enter_submit_button);
        btnFinishCancel = (Button) findViewById(R.id.finish_cancel_button);

        btnEnterSubmit.setOnClickListener(this::EnterSubmit);
        btnFinishCancel.setOnClickListener(this::FinishCancel);
    }

    private void Enter(View view) {

    }

    private void Submit(View view) {

    }

    private void Finish(View view) {

    }

    private void Cancel(View view) {

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