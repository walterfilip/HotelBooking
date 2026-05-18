
package org.example.pensionat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class PensionatApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(PensionatApplication.class, args);
    }


    /* todo
    Update booking
    Delete customer
    Update customer
    Fortsätta med att koppla allt till frontend
    --> vg grejer

     */
}
