
package org.example.hotelbooking;

import org.example.hotelbooking.ui.ConsoleMenu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class HotelBookingApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(HotelBookingApplication.class, args);

//        ConsoleMenu consoleMenu = new ConsoleMenu();
//        consoleMenu.start();

    }


}
