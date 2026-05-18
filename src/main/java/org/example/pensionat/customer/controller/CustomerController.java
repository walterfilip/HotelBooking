package org.example.pensionat.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @GetMapping
    public String customers(){
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

}
