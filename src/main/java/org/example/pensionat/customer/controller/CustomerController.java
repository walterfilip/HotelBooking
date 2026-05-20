package org.example.pensionat.customer.controller;

import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/customers")
public class CustomerController {
   private final CustomerService customerService;



   public CustomerController(CustomerService customerService) {
       this.customerService = customerService;
   }


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

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        System.out.println("email: " + email);
        System.out.println("password: " + password);

        if (!customerService.loginCustomer(email, password)) {
            return "redirect:/";
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
