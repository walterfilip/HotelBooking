package org.example.pensionat.booking.controller;

import org.example.pensionat.booking.model.Booking;
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

    public BookingController(BookingService bookingService, CustomerService customerService, RoomService roomService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.roomService = roomService;
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
        int totalPrice = bookingService.getTotalPrice(
                room,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate),
                extraBed
        );

        model.addAttribute("customer", activeCustomer);
        model.addAttribute("room", room);
        model.addAttribute("roomId", roomId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("extraBed", extraBed);
        model.addAttribute("totalPrice", totalPrice);

        return "booking-form";
    }

    @PostMapping
    public String createBooking(

            @RequestParam Long roomId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "false") boolean extraBed,
            Model model
    ) {

        Customer activeCustomer = customerService.activeCustomer;

        if (activeCustomer == null) {

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

        model.addAttribute(
                "message",
                "Bokning skapad!"
        );

        return "booking-result";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(
            @PathVariable Long id,
            Model model
    ) {
        bookingService.cancelBooking(id);

        model.addAttribute(
                "message",
                "Bokning avbruten!"
        );

        return "booking-result";
    }

    @GetMapping("/changedate/{id}")
    public String changedate(
            @PathVariable Long id,
            Model model
    ) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "customers-date-selection";
    }

    @PostMapping("/changedate/{id}")
    public String changeDateBooking(

            @PathVariable("id") Long bookingId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            Model model
    ) {
        Booking booking = bookingService.getBookingById(bookingId);

        CreateBookingRequest request =
                new CreateBookingRequest(
                        booking.getCustomerId(),
                        booking.getRoom().getId(),
                        startDate,
                        endDate,
                        booking.isExtraBed()
                );

        bookingService.changeBookingDate(request, bookingId);

        model.addAttribute(
                "message",
                "Bokning ändrad!"
        );

        return "booking-result";
    }
}

