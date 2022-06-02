package com.example.girl_scout_cookies;

import android.app.Activity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMap {
    public static boolean mapExists(String s,Connection conn){
        if(ConnectionHelp.readFromDatabase(conn,"Select * FROM Map Where mapCode="+s+";") != null){
            ConnectionHelp.closeConnection(conn);
            return true;
        }else{
            ConnectionHelp.closeConnection(conn);
            return false;
        }
    }

    public static boolean addressExists(String adressID,Connection conn) {
        if(ConnectionHelp.readFromDatabase(conn,"Select * FROM Main Where addressID="+adressID+";") != null){
            ConnectionHelp.closeConnection(conn);
            return true;
        }else{
            ConnectionHelp.closeConnection(conn);
            return false;
        }
    }

    public static ResultSet getAdressesFMID(String mapID,Connection conn){
        return ConnectionHelp.readFromDatabase(conn,"SELECT addressID, colorID FROM Main WHERE mapID="+mapID+";");
    }
    public static String getMapId(String mapname,Connection connection){
        ResultSet set = ConnectionHelp.readFromDatabase(connection,"SELECT mapID FROM Map where mapCode="+mapname+";");
        try {
            mapname=set.getString(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  mapname;
    }
    public static ResultSet getAddressesFMN(String mapname,Connection conn){
        return getAdressesFMID(getMapId(mapname,conn),conn);
    }

    /**
     * Tries to create a table in the database of connection conn
     * @param tablename tablename with column names and types behind it between brackets
     * @param conn connection to database
     */
    public static void createTable(String tablename, Connection conn) {
        String query = "CREATE TABLE " + tablename + ";";
        ConnectionHelp.updateDatabase(conn, query);
    }
}
