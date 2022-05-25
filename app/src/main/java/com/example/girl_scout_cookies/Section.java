package com.example.girl_scout_cookies;

import java.util.List;

import android.graphics.Color;
import android.location.Address;

public class Section {
    private List<Address> addresses;
    private int color;

    public Section(List<Address> addresses, int color) {
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
