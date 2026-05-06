package org.example.hotelbooking.config;



import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {


    private static final Properties p = new Properties();
    private static String URL;
    private static String USER;
    private static String PASSWORD;


    static {
        try (InputStream in = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("config.properties")){

            if(in == null){
                throw new RuntimeException("Properties file not found!");
            }

            p.load(in);

            URL = p.getProperty("URL");
            USER = p.getProperty("USER");
            PASSWORD = p.getProperty("PASSWORD");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}