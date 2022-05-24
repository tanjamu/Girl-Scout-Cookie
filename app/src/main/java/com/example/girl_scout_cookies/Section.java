package com.example.girl_scout_cookies;

import java.util.List;

import android.graphics.Color;
import android.location.Address;

public class Section {
    private List<Address> addresses;
    private Color color;

    public Section(List<Address> addresses, Color color) {
        this.addresses = addresses;
        this.color = color;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void addAddresses(List<Address> addresses) {
        this.addresses.addAll(addresses);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
