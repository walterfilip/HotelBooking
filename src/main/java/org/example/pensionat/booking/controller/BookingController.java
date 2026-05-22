package org.example.pensionat.booking.controller;

import org.example.pensionat.booking.model.CreateBookingRequest;
import org.example.pensionat.booking.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;


@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String bookings(){
        return "bookings";
    }

    @GetMapping("/form")
    public String showBookingForm(
            @RequestParam(defaultValue = "false") boolean extraBed,
            Model model
      ) {
        model.addAttribute("extraBed", extraBed);
        return "booking-form";
    }

    @PostMapping
    public String createBooking(
            //customerid hårdkodad till 1 just nu i rooms.html
            @RequestParam Long customerId,
            @RequestParam Long roomId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam (defaultValue = "false") boolean extraBed
    ) {

        CreateBookingRequest request =
                new CreateBookingRequest(
                        customerId,
                        roomId,
                        startDate,
                        endDate,
                        extraBed
                );

        bookingService.createBooking(request);

        return "bookings";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
        return "bookings";
    }

}

