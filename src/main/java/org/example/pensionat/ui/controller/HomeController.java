package org.example.pensionat.ui.controller;

import org.example.pensionat.customer.client.CustomerClient;
import org.example.pensionat.customer.model.CustomerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class
HomeController {

    private final CustomerClient customerClient;

    public HomeController(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @GetMapping("/")
    public String home(@SessionAttribute
             (value = "customerId", required = false) Long customerId, Model model)
    {
        model.addAttribute(
                "title",
                "Välkommen till Hotellbokning"
        );
        model.addAttribute(
                "subtitle",
                "Sök lediga rum och boka"
        );

        if (customerId != null) {
            CustomerResponse customer = customerClient.getCustomer(customerId);
            model.addAttribute("customer", customer);
        }

        return "index";
    }
}