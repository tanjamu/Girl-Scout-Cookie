package com.example.girl_scout_cookies;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection con;
    String uname, pass, ip, port, database;

    @SuppressLint("NewApi")
    public Connection connectionClass() {
        ip = "172.1.1.0";
        database = "id18911941_girlscoutcookies";
        uname = "id18911941_girlscoutcookiesapp";
        pass = ")g?u]=U2U+tI<erV";
        port = "21";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://"+ip+":"+port+";"+"databasename="+database+";user="+uname+";password="+pass+";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (Exception ex) {
            Log.e("Error ", ex.getMessage());
        }

        return connection;
    }
}
