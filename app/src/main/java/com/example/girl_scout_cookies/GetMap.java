package com.example.girl_scout_cookies;

import android.app.Activity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMap {
    //functions with prepaired statments for sql
    //functions to check for existence
    public static boolean mapExists(String mapName, Connection conn) {
        if (ConnectionHelp.readFromDatabase(conn, "Select * FROM Maps Where Name=" + mapName + ";") != null) {
            ConnectionHelp.closeConnection(conn);
            return true;
        } else {
            ConnectionHelp.closeConnection(conn);
            return false;
        }
    }

    public static boolean addressExists(String adressID, String MapId, Connection conn) {
        try {
            if (ConnectionHelp.readFromDatabase(conn, "Select * FROM Main Where adressID=" + adressID + " AND mapID=" + MapId + ";").next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean adressexistsInA(double latitude,double longitude,Connection connection){
        try {
            if (ConnectionHelp.readFromDatabase(connection, "Select * FROM Addresses Where latitude=" + latitude + " AND longitude=" + longitude + ";").next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static ResultSet getAddresses(String mapID, Connection conn) {
        return ConnectionHelp.readFromDatabase(conn, "SELECT adressID, colorID FROM Main WHERE mapID=" + mapID + ";");
    }

    public static String getMapId(String mapname, Connection connection) {
        ResultSet set = ConnectionHelp.readFromDatabase(connection, "SELECT mapID FROM Maps where Name=" + mapname + ";");
        try {
            mapname = set.getString(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapname;
    }



    public static void updateAddress(String adressID, String colorID, String mapID, Connection connection) {
        if (addressExists(adressID, mapID, connection)) {
            ConnectionHelp.updateDatabase(connection, "UPDATE Main SET colorID=" + colorID + "WHERE adressId=" + adressID + "AND mapID=" + mapID + ";");
        }else{
            createAddress(adressID,colorID,mapID,connection);
        }
    }

    private static void createAddress(String adressID, String colorID, String mapID, Connection connection) {
        ConnectionHelp.updateDatabase(connection, "INSERT INTO MAIN(mapID,adressID,colorID) VALUES(" + mapID + "," + adressID + "," + colorID + ");");
    }

    private static void createAddressinA(double latitude,double longitude, Connection connection) {
        ConnectionHelp.updateDatabase(connection, "INSERT INTO Address(longitude,latitude) VALUES(" + longitude+","+latitude + ")");
    }

    private static Integer getAddressId(double latitude,double longitude, Connection connection) {
        ResultSet r = ConnectionHelp.readFromDatabase(connection, "SELECT id FROM Address WHERE longitude=" + longitude +"AND latitude="+latitude+ ";");
        int res = -1;
        try {
            res = r.getInt(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static int getAddressIDSafe(double latitude, double longitude, Connection connection) {
        if(!adressexistsInA(latitude,longitude,connection)){
            createAddressinA(latitude,longitude,connection);
        }
        return getAddressId(latitude,longitude,connection);
    }

    private static void removeAdress(int mapId,int colorid,int addressid,Connection connection){
        ConnectionHelp.updateDatabase(connection,"DELETE FROM MAIN WHERE mapId="+mapId+" AND colorId="+colorid+"AND addressId="+addressid+";");
    }


}
