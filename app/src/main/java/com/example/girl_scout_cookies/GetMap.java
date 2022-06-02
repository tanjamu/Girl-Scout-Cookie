package com.example.girl_scout_cookies;

import android.app.Activity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMap {
    public static boolean mapExists(String s,Connection conn){
        if(ConnectionHelp.readFromDatabase(conn,"Select * FROM Maps Where Name="+s+";") != null){
            ConnectionHelp.closeConnection(conn);
            return true;
        }else{
            ConnectionHelp.closeConnection(conn);
            return false;
        }
    }

    public static boolean addressExists(String adressID,Connection conn) {
        if(ConnectionHelp.readFromDatabase(conn,"Select * FROM Main Where adressID="+adressID+";") != null){
            ConnectionHelp.closeConnection(conn);
            return true;
        }else{
            ConnectionHelp.closeConnection(conn);
            return false;
        }
    }

    public static ResultSet getAdressesFMID(String mapID,Connection conn){
        return ConnectionHelp.readFromDatabase(conn,"SELECT adressID, colorID FROM Main WHERE mapID="+mapID+";");
    }
    public static String getMapId(String mapname,Connection connection){
        ResultSet set =ConnectionHelp.readFromDatabase(connection,"SELECT mapID FROM Maps where Name="+mapname+";");
        try {
            mapname=set.getString(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  mapname;
    }
    public static ResultSet getAdressesFMN(String mapname,Connection conn){
        return getAdressesFMID(getMapId(mapname,conn),conn);
    }
}
