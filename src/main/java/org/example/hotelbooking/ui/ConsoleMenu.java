package org.example.hotelbooking.ui;

import org.example.hotelbooking.config.DatabaseConnection;
import org.springframework.boot.SpringApplication;

import java.sql.Connection;
import java.sql.SQLException;

public class ConsoleMenu {

    public void start() throws SQLException {

        System.out.println("Det här är ett test");

        try {
            Connection connection = DatabaseConnection.connect();

            if (connection != null) {
                System.out.println("connection successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("connection failed");
        }
    }
}
