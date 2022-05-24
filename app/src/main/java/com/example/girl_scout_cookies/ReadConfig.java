package com.example.girl_scout_cookies;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
    private static Properties prop = new Properties();
    private static FileInputStream ip = null;

    public ReadConfig(){
        try {
            ip = new FileInputStream("src/config.properties");
            prop.load(ip);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getProp(String property) {
        return prop.getProperty(property);
    }
}
