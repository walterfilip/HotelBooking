
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

     KLAR, Registrera användare   - KLAR, men behöver felhantering av kundregistrering i customer-form, just nu kan man skriva vadsom tror jag.
     KLAR, Användare ska kunna ändra sina uppgifter  - Filip KLAR går ändra namn och telefon, även byta lösenord.
     Användare ska kunna ta bort  konto (Får bara tas bort om kunden inte har några aktiva bokningar kopplade till sig.
     Måste ha tydligt felmeddelande).

     skapa en kryptering Filip

    Startsida - Knappar på startsidan, mina bokningar/min sida och logga ut, active user null? linda
    bekräftelse av bokning - mappa till riktig bokning - KLAR, typ, just nu displayas bara priset per dag och tar inte extrasäng i hänsyn.
    Datumsök - Stina
    Räkna ut toatalpris, rum+extrasäng*dagar=total Stina

    minst 10 rum
   Tydliga felmedelanden som visas för användaren


    kolla igenom så felmeddelanden skrivs ut i  frontend, just nu crashar programmet om man försöker boka om datum till ett datum som varit.
    även ändra i avboka så det inte går avboka bokningar som varit.


VG
skriva 4 enhetstester?


    Extras?
    * sortera bokningar i datumordning.
    *Skapa en autoupdate funktion?
    *spärra fullbokade datum och ändra datumdagar?


     */
}
