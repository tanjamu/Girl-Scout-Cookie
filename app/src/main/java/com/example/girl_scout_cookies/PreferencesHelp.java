package com.example.girl_scout_cookies;

public class PreferencesHelp {
    /**
     * adds the parameters to the prefrences
     * @param user string to be stored in the user preference, this is supposed to be the login name for the database
     * @param password string to be stored in the password preference, this is supposed to be the password for the database login
     * @param database string to be stored in the database preference, this is supposed to be the database name of the database
     * @param ip string to be stored in the ip preference, this is supposed to be the ip address for the database
     */
    public static void setUrl(String user, String password, String database, String ip) {
        MainActivity.preferences.edit().putString("user", user).apply();
        MainActivity.preferences.edit().putString("password", password).apply();
        MainActivity.preferences.edit().putString("database", database).apply();
        MainActivity.preferences.edit().putString("ip", ip).apply();
    }

    //gives the user name
    public static String getUser() {
        return MainActivity.preferences.getString("user", "def_user");
    }


    //gives the url to connect to the database
    public static String getUrl() {
        String user = MainActivity.preferences.getString("user", "def_user");
        String password = MainActivity.preferences.getString("password", "def_password");
        String database = MainActivity.preferences.getString("database", "def_database");
        String ip = MainActivity.preferences.getString("ip", "def_ip");
        return "jdbc:jtds:sqlserver://" + ip + ";database=" + database + ";integratedSecurity=false;encrypt=false;user=" + user + ";password=" + password + ";";
    }
}
