package com.example.girl_scout_cookies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class FillMapActivity extends AppCompatActivity {
    FillMapBackend fillMapBackend;

    TextView mapNameTextView;
    TextView errorTextView; // For displaying error messages to the user
    TextView addressTextView; // For entering addresses to add to the map
    Spinner spinner; // For selecting colors

    Button btnEnterSubmitDelete;
    Button btnCancelSave;

    FillMapFragment fragment; // The map
    GoogleMap mMap;

    /**
     * Sets up the spinner to hold the colors
     */
    private void initializeSpinner() {
        spinner = findViewById(R.id.color_input); // For selecting colors
        spinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        new String[]{"red", "blue", "green", "yellow", "magenta", "cyan", "white"}
                )
        );
        spinner.setVisibility(View.GONE);
    }

    /**
     * Gets all variables from the activity and matches them to the local variables
     */
    public void initializeMap(GoogleMap mMap) { ;
        mMap.setOnCircleClickListener(this::onCircleClick);
    }

    private void initializeVariables() {
        setContentView(R.layout.activity_fill_map);

        mapNameTextView = findViewById(R.id.top_text_view);
        mapNameTextView.setText(getIntent().getStringExtra(ToMap.MAP_NAME));

        errorTextView = findViewById(R.id.error_text_view); // For displaying error messages to the user
        addressTextView = findViewById(R.id.address_input); // For entering addresses to add to the map

        initializeSpinner();

        fragment = (FillMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        btnEnterSubmitDelete = findViewById(R.id.enter_submit_delete_button);
        btnCancelSave = findViewById(R.id.cancel_save_button);

        btnEnterSubmitDelete.setOnClickListener(this::EnterSubmitDelete);
        btnCancelSave.setOnClickListener(this::CancelSave);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fillMapBackend = new FillMapBackend(
                this,
                getIntent().getIntExtra(ToMap.MAP_ID, -1)
        );

        // Initialize all variables by finding them from the view
        initializeVariables();
    }

    /**
     * Handles what happens when a circle on the map is clicked.
     * Selects the circle parameter and allows to delete it
     * @param circle - the circle to be deleted
     */
    public void onCircleClick(Circle circle) {
        fillMapBackend.onCircleClick(circle);
        swapToDeleteAddress();
    }

    /**
     * Shows the addressTextView, Enter button and Save button.
     * Hides other components
     */
    private void swapToEnterAddress() {
        btnEnterSubmitDelete.setText(R.string.enter);
        btnCancelSave.setText(R.string.save);
        spinner.setVisibility(View.GONE);
        addressTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Shows the spinner, Submit button and Cancel button.
     * Hides other components
     */
    private void swapToSubmitAddress() {
        btnEnterSubmitDelete.setText(R.string.submit);
        btnCancelSave.setText(R.string.cancel);
        spinner.setVisibility(View.VISIBLE);
        addressTextView.setVisibility(View.GONE);
    }

    /**
     * Shows the Delete button and Cancel button.
     * Hides other components
     */
    private void swapToDeleteAddress() {

        btnEnterSubmitDelete.setText(R.string.delete);
        btnCancelSave.setText(R.string.cancel);
        spinner.setVisibility(View.GONE);
        addressTextView.setVisibility(View.GONE);
    }

    /**
     * Handles the click for the buttons Cancel and Save.
     * Updates button texts
     * @param view the current view
     */
    private void CancelSave(View view) {
        swapToEnterAddress();
        if (btnCancelSave.getText().equals(getString(R.string.cancel))) {
            fillMapBackend.Cancel();
        } else {
            fillMapBackend.Save();
            // Return to home screen
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    /**
     * Handles the click for the buttons Enter, Submit and Delete.
     * Updates button texts
     * @param view the current view
     */
    private void EnterSubmitDelete(View view) {
        if (btnEnterSubmitDelete.getText().equals(getString(R.string.enter))) {
            swapToSubmitAddress();
        } else {
            swapToEnterAddress();
        }

        if (btnEnterSubmitDelete.getText().equals(getString(R.string.enter))) {
            String searchString = addressTextView.getText().toString();
            if (!fillMapBackend.Enter(searchString))
                swapToEnterAddress(); // Search not succeeded
        } else if (btnEnterSubmitDelete.getText().equals(getString(R.string.submit))) {
            fillMapBackend.Submit();
        } else {
            fillMapBackend.Delete();
        }
    }

    public int getSpinnerColorIndex() {
        return spinner.getSelectedItemPosition();
    }

    public Marker addMarker(LatLng latLng) {
        return fragment.addMarker(latLng);
    }

    public Circle addCircle(LatLng latLng, int color) {
        return fragment.addCircle(latLng, color);
    }

    public void setErrorText(int resid) {
        errorTextView.setText(resid);
    }

    public void clearErrorText() {
        errorTextView.clearComposingText();
    }
}