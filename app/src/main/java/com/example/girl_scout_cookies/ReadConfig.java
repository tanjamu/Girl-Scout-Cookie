package com.example.girl_scout_cookies;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.ActivityCompat.requestPermissions;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
    private static Properties prop = new Properties();
    private FileInputStream ip = null;
    private File f = null;

    public ReadConfig(Activity activity){
        try {
            requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);
//            SharedPreferences.Editor editor = activity.getSharedPreferences("iets", MODE_PRIVATE).edit();
//            editor.putString("User", "test_login_name");
//            editor.putString("Password", "password");
//            editor.putString("Database", "TestDB");
//            editor.putString("IP", "145.116.137.26");
//            editor.apply();
            f = new File("app/config.properties");
            ip = new FileInputStream(f);
            prop.load(ip);
        } catch (Exception e){
            e.printStackTrace();
            if(f.canRead()){
                System.out.println("read");
            }
            if(f.canExecute()){
                System.out.println("execute");
            }
            if(f.canWrite()){
                System.out.println("write");
            }
            if(f.exists()){
                System.out.println("exists");
            }
            if(f.isFile()){
                System.out.println("is file");
            }
            if(f.isDirectory()){
                System.out.println("is directory");
            }
        }
    }

    public static String getProp(String property) {
        return prop.getProperty(property);
    }
}
