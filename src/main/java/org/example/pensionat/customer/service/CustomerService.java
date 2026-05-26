package org.example.pensionat.customer.service;


import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.CreateCustomerRequest;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    public Customer activeCustomer;

    public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer(request.firstName(), request.lastName(), request.email(), request.phoneNumber(), request.password());
        return customerRepository.save(customer);
    }


    public boolean loginCustomer(String email, String password) {


        if (email == null || email.isBlank()) {
            return false;
        }
        if (password == null || password.isBlank()) {
            return false;
        }

        try {
            Customer customer = customerRepository.findByEmail(email);
            String dbPassword = customer.getPassword();
            if (!password.equals(dbPassword)) {
                System.out.println("login failed");
                return false;
            }
            if (customer == null) {
                return false;
            }
            activeCustomer = customer;
        } catch (NullPointerException e) {

            System.out.println("login failed");
            return false;
        }


        System.out.println("login successful");
        return true;
    }


    public void updateProfile(CreateCustomerRequest request) {
        Customer customer = customerRepository.findByEmail(request.email());
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPassword(request.password());
        customerRepository.save(customer);

        activeCustomer = customer;


    }


    public boolean checkPassword(String password, String repeatPassword) {

        if (password == null || password.isBlank()) {
            return false;

        }
        if (repeatPassword == null || repeatPassword.isBlank()) {
            return false;

        }
        Customer customer = customerRepository.findByEmail(activeCustomer.getEmail());
        if (!password.equals(customer.getPassword())) {
            System.out.println(customer.getPassword() + " " + password);
            throw new IllegalArgumentException("passwords don't match");

        }
        return true;
    }


}
