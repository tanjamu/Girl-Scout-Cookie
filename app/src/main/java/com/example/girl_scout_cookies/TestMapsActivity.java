package com.example.girl_scout_cookies;

import androidx.fragment.app.FragmentActivity;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.girl_scout_cookies.databinding.ActivityTestMapsBinding;
import android.location.Geocoder;
import android.content.Context;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TestMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTestMapsBinding binding;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String message = getIntent().getStringExtra("Girl-Scout-Cookies.MESSAGE");

        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> list = new LinkedList<Address>();
        try {
            list = geocoder.getFromLocationName(message, 1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        latLng = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng huygens = new LatLng(51.82409973091153, 5.868760067962153);
        Circle circle = mMap.addCircle(new CircleOptions()
            .center(latLng)
            .radius(5)
            .strokeColor(Color.RED)
            .fillColor(Color.RED));
        // mMap.addMarker(new MarkerOptions().position(latLng).title("Huygensgebouw"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
    }
}