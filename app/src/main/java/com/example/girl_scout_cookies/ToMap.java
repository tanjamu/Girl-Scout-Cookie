package com.example.girl_scout_cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class ToMap extends AppCompatActivity {
    Connection connect;
    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_map);
        // Get the Intent that started this activity and extract the string
        android.content.Intent intent = getIntent();
        String message = getIntent().getStringExtra("Girl-Scout-Cookies.MESSAGE");
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }

    public void getTextFromSQL(View view) {
        TextView tx1 = findViewById(R.id.textView3);
        TextView tx2 = findViewById(R.id.textView4);


        ConnectionHelper connectionHelper = new ConnectionHelper();
//            connect = connectionHelper.connectionClass();

        try {

            String dbURL = connectionHelper.ConnectionURL;
            String user = connectionHelper.uname;
            String pass = connectionHelper.pass;
            connect = DriverManager.getConnection(dbURL, user, pass);
            if (connect != null) {
                DatabaseMetaData dm = (DatabaseMetaData) connect.getMetaData();
                tx1.setText(dm.getDriverName());
//                System.out.println("Driver version: " + dm.getDriverVersion());
//                System.out.println("Product name: " + dm.getDatabaseProductName());
//                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connect != null && !connect.isClosed()) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

//            if (connect != null) {
//                String query = "SELECT * FROM test2";
//                Statement st = connect.createStatement();
//                ResultSet rs = st.executeQuery(query);
//
//                ConnectionResult = "Successfully connected";
//
//                while (rs.next()) {
//                    tx1.setText(rs.getString(1));
//                    tx2.setText(rs.getString(2));
//                }
//            } else {
//                ConnectionResult = "Check Connection";
//            }
//        } catch(Exception ex) {
//            Log.e("Error ", ex.getMessage());
//        }
//        tx2.setText(ConnectionResult);
//}
}