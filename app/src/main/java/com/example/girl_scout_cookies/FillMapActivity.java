package com.example.girl_scout_cookies;


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
    public static final String MAP_NAME = "Girl-Scout-Cookies.MAP_NAME";
    public static final String MAP_ID = "Girl-Scout-Cookies.MAP_ID";

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
                        MyColor.getColorStrings()
                )
        );
        spinner.setVisibility(View.GONE);
    }

    /**
     * Initializes the map and the CircleClickListener
     * Gets called when the map is ready
     *
     * @param mMap the map to pass to the FillMapActivity
     */
    public void initializeMap(GoogleMap mMap) {
        this.mMap = mMap;
        this.mMap.setOnCircleClickListener(this::onCircleClick);

        fillMapBackend.loadMap();
    }

    /**
     * Gets all variables from the activity and matches them to the local variables
     */
    private void initializeVariables() {
        setContentView(R.layout.activity_fill_map);

        mapNameTextView = findViewById(R.id.top_text_view);
        mapNameTextView.setText(getIntent().getStringExtra(MAP_NAME));

        errorTextView = findViewById(R.id.error_text_view); // For displaying error messages to the user
        addressTextView = findViewById(R.id.address_input); // For entering addresses to add to the map

        initializeSpinner(); // Handles all spinner related initialisation

        fragment = (FillMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        btnEnterSubmitDelete = findViewById(R.id.enter_submit_delete_button);
        btnCancelSave = findViewById(R.id.cancel_save_button);

        btnEnterSubmitDelete.setOnClickListener(this::EnterSubmitDelete);
        btnCancelSave.setOnClickListener(this::CancelSave);
    }

    @Override
    //called upon creation of the activity (when the screen starts to show the activity)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fillMapBackend = new FillMapBackend(
                this,
                getIntent().getIntExtra(MAP_ID, -1)
        );

        // Initialize all variables by finding them from the view
        initializeVariables();
    }

    /**
     * Handles what happens when a circle on the map is clicked.
     * Selects the circle parameter and allows to delete it
     *
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
     *
     * @param view the current view
     */
    private void CancelSave(View view) {
        if (btnCancelSave.getText().equals(getString(R.string.cancel))) {
            fillMapBackend.Cancel();
        } else {
            fillMapBackend.Save();
            // Return to home screen
            startActivity(new Intent(this, MainActivity.class));
        }
        swapToEnterAddress();
    }

    /**
     * Handles the click for the buttons Enter, Submit and Delete.
     * Updates button texts
     *
     * @param view the current view
     */
    private void EnterSubmitDelete(View view) {
        if (btnEnterSubmitDelete.getText().equals(getString(R.string.enter))) {
            String searchString = addressTextView.getText().toString();
            if (!fillMapBackend.Enter(searchString)) {
                swapToEnterAddress(); // Search not succeeded
                return;
            }
        } else if (btnEnterSubmitDelete.getText().equals(getString(R.string.submit))) {
            fillMapBackend.Submit();
        } else {
            fillMapBackend.Delete();
        }

        if (btnEnterSubmitDelete.getText().equals(getString(R.string.enter))) {
            swapToSubmitAddress();
        } else {
            swapToEnterAddress();
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