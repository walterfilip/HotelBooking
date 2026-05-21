package org.example.pensionat.customer.controller;

import org.apache.catalina.User;
import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.service.BookingService;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.service.CustomerService;
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

   public CustomerController(CustomerService customerService, BookingService bookingService) {
       this.customerService = customerService;
       this.bookingService = bookingService;
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
    public String showCustomerForm() {
        return "customer-form";
    }

    @PostMapping
    public String createCustomer() {

        return "redirect:/bookings/form";
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
        }  // ska bo i restControler?

        return "redirect:/customers"; // ???
    }


    @GetMapping("/logedinpage") // ändra namn
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
