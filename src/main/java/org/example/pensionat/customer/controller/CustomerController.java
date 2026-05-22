package org.example.pensionat.customer.controller;

import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.service.BookingService;
import org.example.pensionat.customer.model.CreateCustomerRequest;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.service.CustomerService;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/customers")
public class CustomerController {

   private final CustomerService customerService;
   private final BookingService bookingService;
   private final RoomService roomService;

   public CustomerController(CustomerService customerService, BookingService bookingService, RoomService roomService) {
       this.customerService = customerService;
       this.bookingService = bookingService;
       this.roomService = roomService;
   }

    @GetMapping
    public String customers(Model model) {
        Customer active = customerService.activeCustomer;
        List<Booking> currentBooking = bookingService.getBookingByCustomerId(active.getId());
            model.addAttribute(
                    "bookings", currentBooking
            );
            model.addAttribute(
                    "customer", active
            );
            model.addAttribute("activeStatus", BookingStatus.ACTIVE);

        return "customers";
    }



    @GetMapping("/form")
    public String showCustomerForm(

             @RequestParam Long roomId,
             @RequestParam String startDate,
             @RequestParam String endDate,
             @RequestParam(defaultValue = "false") boolean extraBed,

             Model model
    ) {

       model.addAttribute("roomId", roomId);
       model.addAttribute("startDate", startDate);
       model.addAttribute("endDate", endDate);
       model.addAttribute("extraBed", extraBed);

        return "customer-form";
    }

    @PostMapping
    public String createCustomer(

            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phoneNumber
    ) {

       CreateCustomerRequest request = new CreateCustomerRequest(
               firstName,
               lastName,
               email,
               phoneNumber
       );

       customerService.createCustomer(request);

       return "redirect:/customers";
    }

    @PostMapping("/booking")
    public String createCustomerWhileBooking(

            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phoneNumber,

            @RequestParam Long roomId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam boolean extraBed,

            Model model

    ){

        CreateCustomerRequest request = new CreateCustomerRequest(
                firstName,
                lastName,
                email,
                phoneNumber
        );

        Customer customer = customerService.createCustomer(request);

        customerService.activeCustomer = customer;

        Room room = roomService.getRoomById(roomId);

        model.addAttribute("customer", customer);
        model.addAttribute("room", room);

        model.addAttribute("roomId", roomId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("extraBed", extraBed);

        return "booking-form";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        System.out.println("email: " + email);
        System.out.println("password: " + password);

        if (!customerService.loginCustomer(email, password)) {
            model.addAttribute("loginError", "Invalid username and/or password");
            return "index";
        }  // ska bo i restController?

        return "redirect:/customers"; // ???
    }


    @GetMapping("/loggedinpage") // ändra namn
    public void customer(Model model) {
        Customer active = customerService.activeCustomer;

        model.addAttribute(
                "customer",
                active.getFirstName()
        );
    }

    @GetMapping("/settingspage") // ändra namn
    public void settings(Model model) {
        Customer active = customerService.activeCustomer;

        model.addAttribute(
                "customer",
                active.getFirstName()
        );
    }
}
