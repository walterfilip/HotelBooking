package org.example.pensionat.booking.controller;

import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.model.CreateBookingRequest;
import org.example.pensionat.booking.service.BookingService;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;

    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
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

            @RequestParam Long roomId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam (defaultValue = "false") boolean extraBed,
            Model model
    )
    {

        Customer activeCustomer = customerService.activeCustomer;

        if(activeCustomer == null){

            return "customer-form";
        }

        CreateBookingRequest request =
                new CreateBookingRequest(
                        activeCustomer.getId(),
                        roomId,
                        startDate,
                        endDate,
                        extraBed
                );

        bookingService.createBooking(request);

        model.addAttribute("customer", activeCustomer);

        return "bookings";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
        return "bookings";
    }

    @GetMapping ("/changedate/{id}")
    public String changedate(@PathVariable Long id, Model model){
        Booking booking =bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "customers-date-selection";
    }

    @PostMapping("/changedate/{id}")
    public String changedateBooking(

        @PathVariable("id") Long bookingId,
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate,
        Model model
    )
        {
            Booking booking = bookingService.getBookingById(bookingId);

            CreateBookingRequest request =
                    new CreateBookingRequest(
                            booking.getCustomer().getId(),
                            booking.getRoom().getId(),
                            startDate,
                            endDate,
                            booking.isExtraBed()
                    );

            bookingService.changeBookingDate(request, bookingId);

            model.addAttribute("customer", booking.getCustomer());

            return "bookings";

    }


}

