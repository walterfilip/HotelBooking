package org.example.hotelbooking.config;



import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

public class DatabaseConnection {

    private static Properties config = new Properties();

    static {
        try {
            FileInputStream input = new FileInputStream("src/main/java/org/example/hotelbooking/config/config.properties");
            config.load(input);
        } catch (Exception e) {
            System.out.println("Kunde inte läsa config!");
        }
    }

    public static Connection connect() throws Exception {
        String url = config.getProperty("URL");
        String user = config.getProperty("USER");
        String password = config.getProperty("PASSWORD");

        return DriverManager.getConnection(url, user, password);
    }
}