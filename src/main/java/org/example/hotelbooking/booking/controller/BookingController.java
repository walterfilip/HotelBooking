package org.example.hotelbooking.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @GetMapping
    public String bookings(){
        return "bookings";
    }
}
