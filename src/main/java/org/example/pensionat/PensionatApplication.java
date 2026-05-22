
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

    Registrera användare   - Nils
    Användare ska kunna ändra sina uppgifter  - Filip
    Användare ska kunna ändra sin bokning (Datum) - Linda
    Användare ska kunna ta bort  konto (Får bara tas bort om kunden inte har några aktiva bokningar kopplade till sig.
    Måste ha tydligt felmeddelande).

    Startsida - knapp för logout actrive user null?
    bekräftelse av bokning - mappa till riktig bokning
    Knappar på startsidan, mina bokningar/min sida och logga ut
    lägga in lösenord i SQL - Filip
    Datumsök - Stina
    Räkna ut toatalpris, rum+extrasäng*dagar=total


    Update login: funkar(loggas in via check mot db(user och password(just nu via phonenumber)
     ) kollar även boknignar mot customerid mot db och displayar
    * sortera bokningar i datumordning.



    minst 10 rum
    Tydliga felmedelanden som visas för användaren

VG
skriva 4 enhetstester?

     */
}
