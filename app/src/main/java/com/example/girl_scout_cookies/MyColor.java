package com.example.girl_scout_cookies;


import android.graphics.Color;


public final class MyColor {

    final static int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.WHITE};

    public static String colorToString(int color) {
        switch (color) {
            case Color.RED:
                return "Red";
            case Color.BLUE:
                return "Blue";
            case Color.GREEN:
                return "Green";
            case Color.YELLOW:
                return "Yellow";
            case Color.MAGENTA:
                return "Magenta";
            case Color.CYAN:
                return "Cyan";
            case Color.WHITE:
                return "White";
            default:
                return "Unknown";
        }
    }

    public static int[] getColors() {
        return colors;
    }

    public static String[] getColorStrings() {
        String[] colorStrings = new String[colors.length];
        for (int i = 0; i < colors.length; ++i) {
            colorStrings[i] = colorToString(colors[i]);
        }
        return colorStrings;
    }

}
