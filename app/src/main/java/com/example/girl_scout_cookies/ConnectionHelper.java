package com.example.girl_scout_cookies;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class ConnectionHelper {
    Connection con;
    String uname, pass, ip, port, database;
    String ConnectionURL = null;

    @SuppressLint("NewApi")
    public Connection connectionClass() {
        ip = "145.116.135.249";
        database = "Test";
        uname = "Test";
        pass = "password";
        port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        ConnectionURL = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ConnectionURL = "jdbc:sqlserver://"+ip+":"+port+";"+"databasename="+database+";user="+uname+";password="+pass+";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (Exception ex) {
            Log.e("Error ", ex.getMessage());
        }

        return connection;
    }
}
