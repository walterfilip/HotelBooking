
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

    Registrera användare   - KLAR, men behöver felhantering av kundregistrering i customer-form, just nu kan man skriva vadsom tror jag.
    Användare ska kunna ändra sina uppgifter  - Filip
    Användare ska kunna ta bort  konto (Får bara tas bort om kunden inte har några aktiva bokningar kopplade till sig.
    Måste ha tydligt felmeddelande).

    Startsida - knapp för logout actrive user null?
    bekräftelse av bokning - mappa till riktig bokning - KLAR, typ, just nu displayas bara priset per dag och tar inte extrasäng i hänsyn.
    Knappar på startsidan, mina bokningar/min sida och logga ut
    Datumsök - Stina
    Räkna ut toatalpris, rum+extrasäng*dagar=total
    skapa en kryptering? för password


    pw inlagt- merge till dev?

    kolla igenom så felmeddelanden skrivs ut i  frontend, just nu crashar programmet om man försöker boka om datum till ett datum som varit.
    även ändra i avboka så det inte går avboka bokningar som varit.




    * sortera bokningar i datumordning.



    minst 10 rum
    Tydliga felmedelanden som visas för användaren

VG
skriva 4 enhetstester?

Extra? spärra fullbokade datum och ändra datumdagar?

     */
}
