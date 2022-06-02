package com.example.girl_scout_cookies;

import static android.graphics.Color.YELLOW;

import static com.example.girl_scout_cookies.MyColor.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.Object;
import java.util.LinkedList;
import java.util.List;

public class FillMapActivity extends AppCompatActivity {
    int mapID;
    TextView errorTextView; // For displaying error messages to the user
    TextView addressTextView; // For entering addresses to add to the map
    Spinner spinner; // For selecting colors
    ArrayAdapter<CharSequence> arrayAdapter; // Fills the spinner

    Button btnEnterSubmit;
    Button btnFinishCancel;

    FillMapFragment fragment; // The map
    Geocoder geocoder; // For transforming address queries into Addresses
    Address address;
    LatLng latLng;
    Marker marker;


    int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,Color.MAGENTA, Color.CYAN, Color.WHITE}; // Change this to all possible color enums
    List<Section> addresses = new LinkedList<>();


    private void initializeVariables() {
        errorTextView = findViewById(R.id.error_text_view); // For displaying error messages to the user
        addressTextView = findViewById(R.id.address_input); // For entering addresses to add to the map
        spinner = findViewById(R.id.color_input); // For selecting colors
        arrayAdapter = new ArrayAdapter<>(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                new String[]{"red", "blue", "green", "yellow", "magenta", "cyan", "white"}); // Fills the spinner

        fragment = (FillMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        geocoder = new Geocoder(getApplicationContext());

        btnEnterSubmit = findViewById(R.id.enter_submit_button);
        btnFinishCancel = findViewById(R.id.finish_cancel_button);

        btnEnterSubmit.setOnClickListener(this::EnterSubmit);
        btnFinishCancel.setOnClickListener(this::FinishCancel);

        spinner.setAdapter(arrayAdapter);
        spinner.setVisibility(View.GONE);

        for (int color : colors) {
            addresses.add(new Section(new LinkedList<>(), color));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fill_map);

        // Initialize all variables by finding them from the view
        initializeVariables();

        // Get the Intent that started this activity and extract the string
        String mapName = getIntent().getStringExtra(ToMap.MAP_NAME);
        mapID = getIntent().getIntExtra(ToMap.MAP_ID, -1);
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.top_text_view);
        textView.setText(mapName);
    }


    private void Enter(View view) {
        // Pressing the enter button
        try {
            // Try to get an Address from the text in the addressTextView
            address = geocoder.getFromLocationName(addressTextView.getText().toString(), 1).get(0); // Fails if there is no result
        } catch (Exception e) {
            // Show error to user and abort
            errorTextView.setText(R.string.error_finding_address);
            swapToPrimary();
            return;
        }
        latLng = new LatLng(address.getLatitude(), address.getLongitude());
        marker = fragment.addMarker(latLng); // Places marker for feedback to user
        errorTextView.clearComposingText();
    }

    private void Submit(View view) {
        // Pressing the submit button
        marker.remove(); // Temporary marker gets removed
        final int colorIndex = spinner.getSelectedItemPosition();
        fragment.addCircle(latLng, colors[colorIndex]);
        addresses.get(colorIndex).addAddress(address);
    }

    private void Finish(View view) {
        // Pressing the finish button

        // TODO: Implement magic SQL stuff
        for (Section s : addresses) {
            int colorID = -1;
            // TODO: Get colorID from database
            String colorIDQuery = "SELECT colorID FROM Color WHERE value = " + s.getColor() + ";";

            for (Address a : s.getAddresses()) {
                int addressID = -1;
                // TODO: Check if address already exists
                String getAddressIDQuery = "SELECT addressID FROM Address WHERE value = '" + a + "';";

                // TODO: If no, insert into the thing
                // TODO: And immediately get the address ID
                String addAddressIDQuery = "INSERT INTO Address(value) VALUES('" + a + "');";


                // TODO: Insert entry into database
                String insertAddress = "INSERT INTO Main(mapID, addressID, colorID) VALUES("
                        + mapID + ", " + addressID + ", " + colorID + ")";

            }
        }

        // Return to home screen
        startActivity(new Intent(this, MainActivity.class));
    }

    private void Cancel(View view) {
        // Pressing the cancel button
        marker.remove(); // Remove temporary marker
    }

    private void swapToPrimary() {
        // Shows the addressTextView, Enter button and Finish button
        // Hides other components
        btnEnterSubmit.setText(R.string.enter);
        btnFinishCancel.setText(R.string.finish);
        spinner.setVisibility(View.GONE);
        addressTextView.setVisibility(View.VISIBLE);
    }

    private void swapToSecondary() {
        // Shows the spinner, Submit button and Cancel button
        // Hides other components
        btnEnterSubmit.setText(R.string.submit);
        btnFinishCancel.setText(R.string.cancel);
        spinner.setVisibility(View.VISIBLE);
        addressTextView.setVisibility(View.GONE);
    }

    private void EnterSubmit(View view) {
        if (btnEnterSubmit.getText().equals(getString(R.string.enter))) {
            swapToSecondary(); // Toggle button text
            Enter(view);
        } else {
            swapToPrimary(); // Toggle button text
            Submit(view);
        }
    }

    private void FinishCancel(View view) {
        if (btnFinishCancel.getText().equals(getString(R.string.finish))) {
            Finish(view);
        } else {
            swapToPrimary(); // Toggle button text
            Cancel(view);
        }
    }

}