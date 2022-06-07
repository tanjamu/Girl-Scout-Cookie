package com.example.girl_scout_cookies;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;

public class FillMapBackend {
    FillMapActivity fillMapActivity;
    final int mapID;

    Collection<Circle> addresses = new HashSet<>(); // Addresses visible on the screen
    Collection<Circle> newAddresses = new HashSet<>(); // Addresses to be added to the database
    Collection<Circle> removedAddresses = new HashSet<>(); // Addresses to be removed from the database

    Marker currentMarker; // Temporary marker used to mark where the current action is taking place
    Circle toDeleteCircle; // Temporary value to keep track of which circle is getting deleted

    final Geocoder geocoder; // For transforming address queries into Addresses

    int[] colors = MyColor.getColors();
    public FillMapBackend(FillMapActivity fillMapActivity, int mapID) {
        this.fillMapActivity = fillMapActivity;
        this.mapID = mapID;

        geocoder = new Geocoder(fillMapActivity.getApplicationContext());
    }

    public void loadMap() {
        Connection connection = ConnectionHelp.openConnection(fillMapActivity);
        ResultSet resultSet = GetMap.getAddresses(mapID, connection);
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                double latitude, longitude;
                int color;
                latitude = resultSet.getDouble(1);
                longitude = resultSet.getDouble(2);
                color = resultSet.getInt(3);
                LatLng latLng = new LatLng(latitude, longitude);
                Circle circle = fillMapActivity.addCircle(latLng, color);
                addresses.add(circle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles what happens when a circle is clicked.
     * Sets the toDeleteCircle too the parameter and adds a marker to it.
     * @param circle the circle that is flagged to be deleted
     */
    public void onCircleClick(Circle circle) {
        LatLng latLng = circle.getCenter();
        currentMarker = fillMapActivity.addMarker(latLng); // Places marker for feedback to user
        toDeleteCircle = circle;
    }

    /**
     * Pressing the Enter button.
     * Looks up the searchString in a Geocoder and adds a marker
     * at the found address if one is found.
     * @param searchString the string to try to match to an address
     * @return returns whether there was an address found
     */
    public boolean Enter(String searchString) {
        Address address;
        try {
            // Try to get an Address from the text in the addressTextView
            address = geocoder.getFromLocationName(searchString, 1).get(0); // Fails if there is no result
        } catch (Exception e) {
            // Show error to user and abort
            fillMapActivity.setErrorText(R.string.error_finding_address);
            return false;
        }
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        currentMarker = fillMapActivity.addMarker(latLng); // Places marker for feedback to user
        fillMapActivity.clearErrorText();
        return true;
    }

    /**
     * Pressing the Submit button.
     * Removes the temporary marker and adds a new circle to the internal sets
     */
    public void Submit() {
        currentMarker.remove(); // Temporary marker gets removed

        final int colorIndex = fillMapActivity.getSpinnerColorIndex();
        final int color = colors[colorIndex];
        Circle circle = fillMapActivity.addCircle(currentMarker.getPosition(), color);

        addresses.add(circle);
        newAddresses.add(circle);
    }

    /**
     * Pressing the Cancel button.
     * Removes the temporary marker.
     */
    public void Cancel() {
        currentMarker.remove(); // Remove temporary marker
    }

    /**
     * Pressing the Delete button.
     * Updates internal circle sets.
     */
    public void Delete() {
        if (toDeleteCircle == null)
            return;

        toDeleteCircle.remove(); // Removes the circle
        addresses.remove(toDeleteCircle);
        newAddresses.remove(toDeleteCircle);
        removedAddresses.add(toDeleteCircle);
    }

    /**
     * Pressing the Save button.
     * Adds all new markers to the database and deletes all removed markers.
     */
    public void Save() {
        Connection connection = ConnectionHelp.openConnection(fillMapActivity);

        for (Circle c : newAddresses) {
            final int colorID = GetMap.getColorID(c.getFillColor(), connection);
            final int addressID = GetMap.getAddressIDSafe(c.getCenter().latitude, c.getCenter().longitude, connection);

            // Insert entry into database
            GetMap.updateAddressSafe(addressID, mapID, colorID, connection);
        }

        for (Circle c : removedAddresses) {
            final int colorID = GetMap.getColorID(c.getFillColor(), connection);
            final int addressID = GetMap.getAddressIDSafe(c.getCenter().latitude, c.getCenter().longitude, connection);

            // Remove entry from database
            GetMap.removeAddress(addressID, mapID, colorID, connection);
        }

        ConnectionHelp.closeConnection(connection);
    }
}
