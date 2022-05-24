package com.example.girl_scout_cookies;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.util.Log;

import androidx.core.app.ActivityCompat;


public class ConnectionHelp {
    private static final String driver = "net.sourceforge.jtds.jdbc.Driver";
    private static String user = "test_login_name";
    private static String password = "password";
    private static String database = "TestDB";
    private static String ip = "145.116.137.26"; //change
    private static String connection = "jdbc:jtds:sqlserver://"+ip+";database="+database+";integratedSecurity=false;encrypt=false;user="+user+";password="+password+";";

    /**
     * tries to connect to database and prints info about whether it succeeded
     * @param con the connection to connect to the database
     * @param activity the activity from which to connect
     * @return connection con after connecting
     */
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

    /**
     * tries to close the connection con and prints info about whether it succeeded
     * @param con the connection to close
     */
    public static void closeConnection(Connection con){
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
    }

    /**
     * tries to executes SQL statement to update database
     * @param con the connection to the database
     * @param query the SQL query to execute
     */
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

    /**
     * tries to execute SQL statement to get information from database
     * @param con the connection to the database
     * @param query the SQL query to execute
     * @return the result set gained from executing the query if successfull, otherwise null
     */
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
