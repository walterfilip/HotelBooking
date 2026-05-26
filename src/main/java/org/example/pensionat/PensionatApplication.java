
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

     KLAR, Användare ska kunna ändra sina uppgifter  - Filip KLAR går ändra namn och telefon, även byta lösenord.
     Användare ska kunna ta bort konto (Får bara tas bort om kunden inte har några aktiva bokningar kopplade till sig.
     Måste ha tydligt felmeddelande). - Stina

     skapa en kryptering Filip

    Räkna ut totalpris, rum+extrasäng*dagar=total Stina


//test
    minst 10 rum - Nils
   Tydliga felmeddelanden som visas för användaren


    kolla igenom så felmeddelanden skrivs ut i  frontend, just nu crashar programmet om man försöker boka om datum till ett datum som varit.
    även ändra i avboka så det inte går avboka bokningar som varit.


VG
skriva 4 enhetstester?


    Extras?
    * sortera bokningar i datumordning.
    *Skapa en autoupdate funktion? -- Nils
    *spärra fullbokade datum och ändra datumdagar?
    *jag gjorde 2 sidor så det står rätt när bokning är gjord/ändrad/borttagen, -
    kanske snyggare med en ifsatts och bara skicka med en motsvarande siffra eller nått

     */
}
