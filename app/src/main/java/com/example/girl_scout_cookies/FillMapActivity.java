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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.Object;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

public class FillMapActivity extends AppCompatActivity {
    int mapID;
    GoogleMap mMap;
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
    Circle circle;


    int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,Color.MAGENTA, Color.CYAN, Color.WHITE}; // Change this to all possible color enums
    Collection<Circle> addresses = new HashSet<>();
    Collection<Circle> newAddresses = new HashSet<>();
    Collection<Circle> removedAddresses = new HashSet<>();


    private void initializeVariables() {
        errorTextView = findViewById(R.id.error_text_view); // For displaying error messages to the user
        addressTextView = findViewById(R.id.address_input); // For entering addresses to add to the map
        spinner = findViewById(R.id.color_input); // For selecting colors
        arrayAdapter = new ArrayAdapter<>(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                new String[]{"red", "blue", "green", "yellow", "magenta", "cyan", "white"}); // Fills the spinner

        fragment = (FillMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        mMap = fragment.getmMap();
        mMap.setOnCircleClickListener(this::onCircleClick);
        geocoder = new Geocoder(getApplicationContext());

        btnEnterSubmit = findViewById(R.id.enter_submit_button);
        btnFinishCancel = findViewById(R.id.finish_cancel_button);

        btnEnterSubmit.setOnClickListener(this::EnterSubmit);
        btnFinishCancel.setOnClickListener(this::FinishCancel);

        spinner.setAdapter(arrayAdapter);
        spinner.setVisibility(View.GONE);
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

    public void onCircleClick(Circle circle) {
        latLng = circle.getCenter();
        marker = fragment.addMarker(latLng); // Places marker for feedback to user
        swapToDeleteAddress();
    }

    private void Enter(View view) {
        // Pressing the enter button
        try {
            // Try to get an Address from the text in the addressTextView
            address = geocoder.getFromLocationName(addressTextView.getText().toString(), 1).get(0); // Fails if there is no result
        } catch (Exception e) {
            // Show error to user and abort
            errorTextView.setText(R.string.error_finding_address);
            swapToEnterAddress();
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

        Circle circle = fragment.addCircle(latLng, colors[colorIndex]);

        addresses.add(circle);
        newAddresses.add(circle);
    }

    private void Finish(View view) {
        // Pressing the finish button

        Connection connection = null;
        ConnectionHelp.connect(connection, this);

        for (Circle c : newAddresses) {
            final int colorID = GetMap.getColorID(c.getFillColor(), connection);
            final int addressID = GetMap.getAddressIDCreateIfNotExists(c.getCenter().latitude, c.getCenter().longitude, connection);

            // Insert entry into database
            GetMap.updateAddressSafe(addressID, mapID, colorID, connection);
        }

        for (Circle c : removedAddresses) {
            final int colorID = GetMap.getColorID(c.getFillColor(), connection);
            final int addressID = GetMap.getAddressIDCreateIfNotExists(c.getCenter().latitude, c.getCenter().longitude, connection);

            // Remove entry from database
            GetMap.removeAddressSafe(addressID, mapID, colorID, connection);

        }

        ConnectionHelp.closeConnection(connection);

        // Return to home screen
        startActivity(new Intent(this, MainActivity.class));
    }

    private void Cancel(View view) {
        // Pressing the cancel button
        marker.remove(); // Remove temporary marker
    }

    private void Delete(View view) {
        // Pressing the delete button
        addresses.remove(circle);
        newAddresses.remove(circle);
        removedAddresses.add(circle);
    }

    private void swapToEnterAddress() {
        // Shows the addressTextView, Enter button and Finish button
        // Hides other components
        btnEnterSubmit.setText(R.string.enter);
        btnFinishCancel.setText(R.string.finish);
        spinner.setVisibility(View.GONE);
        addressTextView.setVisibility(View.VISIBLE);
    }

    private void swapToSubmitAddress() {
        // Shows the spinner, Submit button and Cancel button
        // Hides other components
        btnEnterSubmit.setText(R.string.submit);
        btnFinishCancel.setText(R.string.cancel);
        spinner.setVisibility(View.VISIBLE);
        addressTextView.setVisibility(View.GONE);
    }

    private void swapToDeleteAddress() {
        // Shows the Delete button and Cancel button
        // Hides other components
        btnEnterSubmit.setText(R.string.delete);
        btnFinishCancel.setText(R.string.cancel);
        spinner.setVisibility(View.GONE);
        addressTextView.setVisibility(View.GONE);
    }

    private void EnterSubmit(View view) {
        if (btnEnterSubmit.getText().equals(getString(R.string.enter))) {
            swapToSubmitAddress(); // Toggle button text
            Enter(view);
        } else {
            swapToEnterAddress(); // Toggle button text
            Submit(view);
        }
    }

    private void FinishCancel(View view) {
        if (btnFinishCancel.getText().equals(getString(R.string.finish))) {
            Finish(view);
        } else if (btnFinishCancel.getText().equals(getString(R.string.delete))) {
            Delete(view);
            swapToEnterAddress();
        } else {
            swapToEnterAddress(); // Toggle button text
            Cancel(view);
        }
    }
}