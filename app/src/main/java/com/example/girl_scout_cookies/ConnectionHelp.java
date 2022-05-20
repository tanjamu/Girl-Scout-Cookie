package com.example.girl_scout_cookies;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.sql.DatabaseMetaData;

public class ConnectionHelp {
    private static String driver = "net.sourceforge.jtds.jdbc.Driver";
    private static String user = "test_login_name";
    private static String password = "password";
    private static String database = "TestDB";
    private static String ip = "192.168.56.1";
    private static String connection = "jdbc:jtds:sqlserver://"+ip+";database="+database+";integratedSecurity=false;encrypt=false;user="+user+";password="+password+";";

    private static Statement state = null;
    private static ResultSet result;
    private static PreparedStatement pstate;


    public static Connection connect(Connection con, Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(connection);
            System.out.println("Successfully connected to database.");
        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException ex){
            System.err.println("Couldn't load driver: " + ex.getMessage());
        }
        catch(SQLException ex){
            System.err.println("Couldn't connect to database: " + ex.getMessage());
        }
        return con;
    }

    public static Connection closeConnection(Connection con){
        try{
            if(!con.isClosed()){
                con.close();
                System.out.println("Database closed successfully.");
            }
        }
        catch(NullPointerException ex){
            System.err.println("Couldn't load driver.");
        }
        catch(SQLException ex){
            System.err.println("Couldn't close database.");
        }
        return con;
    }

    public static void updateDatabase(Connection con, String query) {
        try {
            if (con != null) {
                Statement st = con.createStatement();
                st.executeUpdate(query);
                System.out.println("Successfully updated database");
            } else {
                System.err.println("Check connection, no connection found");
            }
        } catch(Exception ex) {
            Log.e("Error ", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static ResultSet readFromDatabase(Connection con, String query) {
        ResultSet rs = null;
        try {
            if (con != null) {
                Statement st = con.createStatement();
                rs = st.executeQuery(query);
                System.out.println("Successfully read from database");
            } else {
                System.err.println("Check connection, no connection found");
            }
        } catch (Exception ex) {
            Log.e("Error ", ex.getMessage());
            ex.printStackTrace();
        }
        return rs;
    }
}
