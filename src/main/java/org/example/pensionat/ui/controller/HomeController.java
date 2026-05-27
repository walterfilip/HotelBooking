package org.example.pensionat.ui.controller;

import org.example.pensionat.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class
HomeController {

    private final CustomerService customerService;

    public HomeController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(
                "title",
                "Välkommen till Hotellbokning"
        );
        model.addAttribute(
                "subtitle",
                "Sök lediga rum och boka"
        );

        model.addAttribute(
                "activeCustomer",
                customerService.activeCustomer
        );

        return "index";
    }
}