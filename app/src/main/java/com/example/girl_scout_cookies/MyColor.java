package com.example.girl_scout_cookies;


import android.graphics.Color;


public final class MyColor {
    // The colors
    final static int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.WHITE};

    /**
     * Returns the string representation of a color in colors
     * @param color the color to convert
     * @return a string representation of the given color
     */
    public static String colorToString(int color) {
        // Gives a String representation for every color in the colors array
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
                return "Unknown"; // Should never be called
        }
    }

    /**
     * @return the list of colors dedicated for the spinner
     */
    public static int[] getColors() {
        return colors;
    }

    /**
     * Returns the list of colors in String form
     * @return the list of colors
     */
    public static String[] getColorStrings() {
        String[] colorStrings = new String[colors.length];
        for (int i = 0; i < colors.length; ++i) {
            colorStrings[i] = colorToString(colors[i]);
        }
        return colorStrings;
    }

}
