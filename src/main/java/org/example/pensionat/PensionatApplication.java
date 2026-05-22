
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


    Startsida - knapp för login, spara inloggad som bool?
    Formulär - if inlogad inget formulär,
    bekräftelse av bokning - gå till ens egen hemsida, lösen?
    Display idividuella rum, lägga till beskrivning
    Knappar på startsidan, mina bokningar och logga ut
    löseord


    Update login: funkar(loggas in via check mot db(user och password(just nu via phonenumber) ) kollar även boknignar mot customerid mot db och displayar
    * kvar att göra en koppling för att hålla koll att man är inloggad
    * eventuellt ändra meny i index så om man är inloggad kan det vara en knapp logga ut?
    * sortera bokningar i datumordning.
    * bygga vidare på "kundsida" skapa funktioner för ändringar av kundinfo, avboka bokningar(knapp inlagd som avbokar)
    * lägga in lösenord i SQL
    * skapa en logout(start och i Customer



    Rumsval ?
    Bara se tillgängliga rum


    skriva 5 enhetstester?

    Linda + Filip - login
    Stina - kopplar rum
    Nils -


     */
}
