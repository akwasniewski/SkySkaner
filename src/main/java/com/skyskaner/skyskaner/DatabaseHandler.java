package com.skyskaner.skyskaner;
import java.sql.*;
import java.util.Properties;

public class DatabaseHandler {
    private static final String url = "jdbc:postgresql://localhost:5432/skyskaner";
    private static final String user = "admin";
    private static final String password = "tanieczyszczeniesprzatanie";
    static Properties props = new Properties();
    static {
        props.setProperty("user",user);
        props.setProperty("password",password);
    }
    static Connection connection=null;
    static void ConnectToPostgres() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skyskaner","admin","tanieczyszczeniesprzatanie");
    }
}
