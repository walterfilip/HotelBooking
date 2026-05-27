package org.example.pensionat.customer.controller;

import jakarta.validation.Valid;
import org.example.pensionat.customer.model.CreateCustomerRequest;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }
}
