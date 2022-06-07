package com.example.girl_scout_cookies;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMap {
    // Functions with prepaired statments for sql
    // Functions to check for existence
    public static boolean mapExists(String mapName, Connection conn) {
        if (ConnectionHelp.readFromDatabase(conn, "SELECT * FROM Maps WHERE Name = '" + mapName + "';") != null) {
            ConnectionHelp.closeConnection(conn);
            return true;
        } else {
            ConnectionHelp.closeConnection(conn);
            return false;
        }
    }

    public static boolean addressExists(int addressID, int mapID, Connection conn) {
        try {
            return ConnectionHelp.readFromDatabase(conn, "SELECT * FROM Main WHERE addressID = " + addressID + " AND mapID = " + mapID + ";").next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean addressExistsInA(double latitude, double longitude, Connection connection){
        try {
            return ConnectionHelp.readFromDatabase(connection, "SELECT * FROM Addresses WHERE latitude=" + latitude + " AND longitude=" + longitude + ";").next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static ResultSet getAddresses(int mapID, Connection conn) {
        return ConnectionHelp.readFromDatabase(conn, "SELECT addressID, colorID FROM Main WHERE mapID = " + mapID + ";");
    }

    public static int getMapID(String mapName, Connection connection) {
        ResultSet set = ConnectionHelp.readFromDatabase(connection, "SELECT mapID FROM Maps WHERE Name = '" + mapName + "';");
        int mapID = -1;
        try {
            mapID = set.getInt(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapID;
    }

    public static int getColorID(int color, Connection connection) {
        ResultSet set = ConnectionHelp.readFromDatabase(connection, "SELECT colorID FROM Color WHERE value = " + color + ";");
        int colorID = -1;
        try {
            colorID = set.getInt(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colorID;
    }

    public static void updateAddressSafe(int addressID, int colorID, int mapID, Connection connection) {
        if (addressExists(addressID, mapID, connection)) {
            ConnectionHelp.updateDatabase(connection, "UPDATE Main SET colorID = " + colorID + "WHERE addressID = " + addressID + "AND mapID = " + mapID + ";");
        }else{
            createAddress(addressID, colorID, mapID, connection);
        }
    }

    private static void createAddress(int addressID, int colorID, int mapID, Connection connection) {
        ConnectionHelp.updateDatabase(connection, "INSERT INTO Main(mapID, addressID, colorID) VALUES(" + mapID + "," + addressID + "," + colorID + ");");
    }

    private static void createAddressInA(double latitude, double longitude, Connection connection) {
        ConnectionHelp.updateDatabase(connection, "INSERT INTO Address(latitude, longitude) VALUES(" + latitude + "," + longitude + ")");
    }

    private static Integer getAddressId(double latitude,double longitude, Connection connection) {
        ResultSet r = ConnectionHelp.readFromDatabase(connection, "SELECT id FROM Address WHERE latitude = " + latitude +"AND longitude = " + longitude + ";");
        int res = -1;
        try {
            res = r.getInt(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static int getAddressIDSafe(double latitude, double longitude, Connection connection) {
        if(!addressExistsInA(latitude,longitude,connection)){
            createAddressInA(latitude,longitude,connection);
        }
        return getAddressId(latitude,longitude,connection);
    }

    public static void removeAddress(int mapID,int colorID,int addressID,Connection connection){
        ConnectionHelp.updateDatabase(connection,"DELETE FROM Main WHERE mapID = " + mapID + " AND colorID = " + colorID + " AND addressID = " + addressID + ";");
    }


}
