package org.example.pensionat.booking.controller;

import org.example.pensionat.booking.model.CreateBookingRequest;
import org.example.pensionat.booking.service.BookingService;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.service.CustomerService;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final RoomService roomService;

    public BookingController(BookingService bookingService, CustomerService customerService , RoomService roomService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @GetMapping
    public String bookings(){
        return "bookings";
    }

    @GetMapping("/form")
    public String showBookingForm(

            @RequestParam Long roomId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "false") boolean extraBed,

            Model model
      ) {

        Customer activeCustomer = customerService.activeCustomer;

        if (activeCustomer == null) {
            return "customer-form";
        }

        Room room = roomService.getRoomById(roomId);

        model.addAttribute("customer", activeCustomer);
        model.addAttribute("room", room);

        model.addAttribute("roomId", roomId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("extraBed", extraBed);

        return "booking-form";

    }

    @PostMapping
    public String createBooking(

            @RequestParam Long roomId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam (defaultValue = "false") boolean extraBed
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

        return "redirect:/customers";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
        return "bookings";
    }

}

