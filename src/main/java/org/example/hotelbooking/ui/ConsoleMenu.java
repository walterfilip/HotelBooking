package org.example.hotelbooking.ui;

import org.example.hotelbooking.config.DatabaseConnection;
import org.springframework.boot.SpringApplication;

import java.sql.Connection;
import java.sql.SQLException;

public class ConsoleMenu {

    public void start() throws SQLException {

        System.out.println("Det här är ett test");

        try {
            Connection connection = DatabaseConnection.getConnection();

            if (connection != null) {
                System.out.println("connection successfull");
            }
        } catch (Exception e) {
            System.out.println("connection failed");
        }
    }
}
