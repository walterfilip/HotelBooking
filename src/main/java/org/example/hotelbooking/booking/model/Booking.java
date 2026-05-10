package org.example.hotelbooking.booking.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.example.hotelbooking.booking.BookingStatus;
import org.example.hotelbooking.customer.model.Customer;
import org.example.hotelbooking.room.model.Room;

import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne(optional = false)
    private Room room;

    @NotNull(message = "StartDatum måste anges")
    @FutureOrPresent(message = "Startdatum kan inte vara bakåt i tiden")
    private LocalDate startDatum;

    @NotNull(message = "En booking måste ha en status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    protected Booking() {
    }

    public Booking(Customer customer, Room room, LocalDate startDatum, BookingStatus status) {
        this.customer = customer;
        this.room = room;
        this.startDatum = startDatum;
        this.status = status;
    }
}










//import lombok.Data;

//import java.time.LocalDate;
//@Data
//public class Booking {
//    private int id;
//    private int customerId;
//    private int roomId;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private boolean extraBed;
//    private boolean status;
//}
