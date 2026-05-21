package org.example.pensionat.booking.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.room.model.Room;

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

    @Column(nullable = false)
    private boolean extraBed = false;

    @NotNull(message = "En bokning måste ha en status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    protected Booking() {
    }

    public Booking(Customer customer, Room room, LocalDate startDate, LocalDate endDate, boolean extraBed, BookingStatus status) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extraBed = extraBed;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isExtraBed() {
        return extraBed;
    }

    public void setExtraBed(boolean extraBed) {
        this.extraBed = extraBed;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}


