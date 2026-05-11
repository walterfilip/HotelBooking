package org.example.hotelbooking.customer.service;


import org.example.hotelbooking.booking.repository.BookingRepository;
import org.example.hotelbooking.customer.model.CreateCustomerRequest;
import org.example.hotelbooking.customer.model.Customer;
import org.example.hotelbooking.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = new  Customer(request.firstName(),request.lastName(),request.email(),request.phoneNumber());
        return customerRepository.save(customer);
    }

}
