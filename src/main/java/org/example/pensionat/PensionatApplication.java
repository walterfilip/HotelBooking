
package org.example.pensionat;

import org.example.pensionat.booking.service.BookingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class PensionatApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(PensionatApplication.class, args);
    }

    //körs när man startar appen
    @Bean
    CommandLineRunner updateBoookings(BookingService bookingService) {
        return args -> bookingService.updateExpiredBookings();
    }
}


    /* Todo
    GREJER VI INTE HAR GJORT ÄN:

    1.   Användare ska kunna ta bort konto (Får bara tas bort om kunden inte har några aktiva bokningar kopplade till sig.
         Måste ha tydligt felmeddelande). - Stina

    2.   skriva 4 enhetstester - Linda (skapa bokning + ändra , ladda rum)  & Nils ( html test?, kundformulär )  Klar ladda användare


    4.   Tydliga felmeddelanden som visas för användaren

    6.   The knapps are buggy?!?!?! NEED FIX. - Linda

    7. bugg när man failar en login, då försvinner title och subtitle när felmeddelande postas. / fix or skip?
    8. byta namn från hotelbooking till pensionat?


Eftermidag
codecleanup, felmedelanden, gå igenom redovisning

Under redovisningen ska ni gå igenom:

En demo av appen där ni visar full funktionalitet(en i gruppen får göra detta)
Visa arkitekturen i appen och förklara varför ni kör på det sättet. Tex om det följer en multi tier arkitektur med controller service model etc
Hur gruppen har fördelat arbetet mellan sig (Jira, trello eller annat)
Vad varje person själv har bidragit med till projektet(Funktioner etc)
Hur det har fungerat att jobba i grupp
Vad som har varit lättare eller svårare med att arbeta i grupp.
Vad ni har lärt er av arbetet
Ni behöver INTE gå igenom och förklara hela kodbasen, jag kommer ställa frågor om specifika delar/funktioner istället.

*/
