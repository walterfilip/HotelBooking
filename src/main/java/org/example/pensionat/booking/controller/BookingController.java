package org.example.pensionat.booking.controller;


import jakarta.validation.Valid;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.model.CreateBookingRequest;
import org.example.pensionat.booking.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins="*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody @Valid CreateBookingRequest request){
        return bookingService.createBooking(request);
    }

}
