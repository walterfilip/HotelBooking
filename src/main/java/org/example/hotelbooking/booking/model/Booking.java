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
    private long id;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne(optional = false)
    private Room room;

    @NotNull(message = "Start datum måste anges")
    @FutureOrPresent(message = "Startdatum kan inte vara bakåt i tiden")
    private LocalDate startDate;

    @NotNull(message = "Slut datum måste anges")
    @FutureOrPresent(message = "slut datum kan inte vara bakåt i tiden")
    private LocalDate endDate;

    // Kanske stämmer ??
    @Column(nullable = false)
    private boolean extraBed;

    @NotNull(message = "En booking måste ha en status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    protected Booking() {
    }

    public Booking(Customer customer, Room room, LocalDate startDate, LocalDate endDate, Boolean extraBed, BookingStatus status) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extraBed = extraBed;
        this.status = status;
    }
}


