package com.example.girl_scout_cookies;

public class PreferencesHelp {
    public static void setUrl(String user, String password, String database, String ip) {
        MainActivity.preferences.edit().putString("user", user).apply();
        MainActivity.preferences.edit().putString("password", password).apply();
        MainActivity.preferences.edit().putString("database", database).apply();
        MainActivity.preferences.edit().putString("ip", ip).apply();
    }

    public static String getUrl() {
        String user = MainActivity.preferences.getString("user", "def_user");
        String password = MainActivity.preferences.getString("password", "def_password");
        String database = MainActivity.preferences.getString("database", "def_database");
        String ip = MainActivity.preferences.getString("ip", "def_ip");
        return "jdbc:jtds:sqlserver://" + ip + ";database=" + database + ";integratedSecurity=false;encrypt=false;user=" + user + ";password=" + password + ";";
    }
}
