package org.example.hotelbooking.models;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Booking {
    private int id;
    private int customerId;
    private int roomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean extraBed;
    private boolean status;
}
