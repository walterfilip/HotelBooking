
package org.example.pensionat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class PensionatApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(PensionatApplication.class, args);
    }


    /* Todo
    GREJER VI INTE HAR GJORT ÄN:

    Registrera användare
    Användare ska kunna ändra sina uppgifter
    Användare ska kunna ändra sin bokning
    Användare ska kunna ta bort  konto (Får bara tas bort om kunden inte har några aktiva bokningar kopplade till sig. Måste ha tydligt felmeddelande).


    Fortsätta med att koppla allt till frontend

    skriva 5 enhetstester?

     */
}
