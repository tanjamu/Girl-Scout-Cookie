package com.example.girl_scout_cookies;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains functions with prepared statements for SQL and functions that check for existence
 */
public class SQLHelp {
    /**
     * Checks whether a map with a certain name exists in the database
     *
     * @param mapName name of the map to be checked
     * @param conn    connection to the database
     * @return whether a map with name mapName exists in the database
     */
    public static boolean mapExists(String mapName, Connection conn) {
        try {
            return ConnectionHelp.readFromDatabase(conn, "SELECT * FROM Map WHERE name = '" + mapName + "';").next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks whether an address with addressID exists in the map with mapID
     *
     * @param addressID ID of address to be checked
     * @param mapID     ID of map to check address in
     * @param conn      connection to the database
     * @return whether addressID exists in map with mapID
     */
    public static boolean addressExists(int addressID, int mapID, Connection conn) {
        try {
            return ConnectionHelp.readFromDatabase(conn, "SELECT * FROM Main WHERE addressID = " + addressID + " AND mapID = " + mapID + ";").next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks whether an address with certain latitude and longitude exists in table Address
     *
     * @param latitude  latitude of address
     * @param longitude longitude of address
     * @param conn      connection to the database
     * @return whether address exists in Address
     */
    private static boolean addressExistsInA(double latitude, double longitude, Connection conn) {
        try {
            return ConnectionHelp.readFromDatabase(conn, "SELECT * FROM Address WHERE latitude=" + latitude + " AND longitude=" + longitude + ";").next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * gets the addresses from the database of the map with a certain mapID
     *
     * @param mapID ID of the map to get addresses from
     * @param conn  connection to the database
     * @return ResultSet of addresses from map with mapID
     */
    public static ResultSet getAddresses(int mapID, Connection conn) {
        return ConnectionHelp.readFromDatabase(conn, "SELECT latitude, longitude, color FROM Main, Address, Color WHERE mapID = " + mapID + " AND Address.addressID = Main.addressID AND Color.colorID = Main.colorID;");
    }

    /**
     * gets the mapID of the map with a certain name
     *
     * @param mapName name of the map
     * @param conn    connection to the database
     * @return mapID belonging to the map with name mapName
     */
    public static int getMapID(String mapName, Connection conn) {
        ResultSet set = ConnectionHelp.readFromDatabase(conn, "SELECT * FROM Map WHERE name = '" + mapName + "';");
        int mapID = -1;
        try {
            set.next(); //to get the first element of ResultSet set
            mapID = set.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapID;
    }

    /**
     * gets the colorID of the color with a certain value
     *
     * @param color the value of the color
     * @param conn  connection to the database
     * @return colorID of color
     */
    public static int getColorID(int color, Connection conn) {
        ResultSet set = ConnectionHelp.readFromDatabase(conn, "SELECT colorID FROM Color WHERE color = " + color + ";");
        int colorID = -1;
        try {
            set.next(); //to get the first element of ResultSet set
            colorID = set.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colorID;
    }

    /**
     * updates color of an address on a certain map safely, if the address doesn't exist it creates the address
     *
     * @param addressID ID of the address to update
     * @param colorID   ID of the color to set the color to
     * @param mapID     ID of the map of the address
     * @param conn      connection to the database
     */
    public static void updateAddressSafe(int addressID, int colorID, int mapID, Connection conn) {
        if (addressExists(addressID, mapID, conn)) {
            ConnectionHelp.updateDatabase(conn, "UPDATE Main SET colorID = " + colorID + "WHERE addressID = " + addressID + "AND mapID = " + mapID + ";");
        } else {
            createAddress(addressID, colorID, mapID, conn);
        }
    }

    /**
     * adds an address to table Main with a certain mapID and colorID
     *
     * @param addressID ID of address to add
     * @param colorID   ID of color to assign to address
     * @param mapID     ID of map of the address
     * @param conn      connection to the database
     */
    private static void createAddress(int addressID, int colorID, int mapID, Connection conn) {
        ConnectionHelp.updateDatabase(conn, "INSERT INTO Main(mapID, addressID, colorID) VALUES(" + mapID + "," + addressID + "," + colorID + ");");
    }

    /**
     * creates an address in the table Address with a certain latitude and longitude
     *
     * @param latitude  latitude of address
     * @param longitude longitude of address
     * @param conn      connection to the database
     */
    private static void createAddressInA(double latitude, double longitude, Connection conn) {
        ConnectionHelp.updateDatabase(conn, "INSERT INTO Address(latitude, longitude) VALUES(" + latitude + "," + longitude + ");");
    }

    /**
     * gets the addressID of the address with a certain latitude and longitude
     *
     * @param latitude  latitude of address
     * @param longitude longitude of address
     * @param conn      connection to the database
     * @return addressID of address
     */
    private static Integer getAddressID(double latitude, double longitude, Connection conn) {
        ResultSet set = ConnectionHelp.readFromDatabase(conn, "SELECT addressID FROM Address WHERE latitude = " + latitude + "AND longitude = " + longitude + ";");
        int res = -1;
        try {
            set.next(); //to get the first element of ResultSet set
            res = set.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * gets the addressID of address with a certain latitude and longitude, also creates address if it doesn't exist in table Address
     *
     * @param latitude  latitude of address
     * @param longitude longitude of address
     * @param conn      connection to the database
     * @return addressID of address
     */
    public static int getAddressIDSafe(double latitude, double longitude, Connection conn) {
        if (!addressExistsInA(latitude, longitude, conn)) {
            createAddressInA(latitude, longitude, conn);
        }
        return getAddressID(latitude, longitude, conn);
    }

    /**
     * removes address with addressID, mapID and colorID from database table Main
     *
     * @param mapID     ID of the map
     * @param colorID   ID of the color
     * @param addressID ID of the address
     * @param conn      connection to the database
     */
    public static void removeAddress(int mapID, int colorID, int addressID, Connection conn) {
        ConnectionHelp.updateDatabase(conn, "DELETE FROM Main WHERE mapID = " + mapID + " AND colorID = " + colorID + " AND addressID = " + addressID + ";");
    }

    /**
     * Tries to create a table in the database of connection conn
     *
     * @param tableName table name with column names and types behind it between brackets
     * @param conn      connection to the database
     */
    public static void createTable(String tableName, Connection conn) {
        String query = "CREATE TABLE " + tableName + ";";
        ConnectionHelp.updateDatabase(conn, query);
    }

    /**
     * Drops a table if it exists
     *
     * @param tableName table name to be dropped
     * @param conn      connection to the database
     */
    public static void dropTable(String tableName, Connection conn) {
        String query = "DROP TABLE IF EXISTS " + PreferencesHelp.getUser() + "." + tableName + ";";
        ConnectionHelp.updateDatabase(conn, query);
    }

    /**
     * adds a color to the table Color of the database
     *
     * @param color int indicating the color
     * @param conn  connection to the database
     */
    public static void addColor(int color, Connection conn) {
        ConnectionHelp.updateDatabase(conn, "INSERT INTO Color(color) Values(" + color + ");");
    }

    /**
     * Makes a Map in the database
     *
     * @param mapName name of the map in the database
     * @param conn    connection to the database
     */
    public static void createMap(String mapName, Connection conn) {
        ConnectionHelp.updateDatabase(conn, "INSERT INTO Map(name) Values('" + mapName + "');");
    }
}

