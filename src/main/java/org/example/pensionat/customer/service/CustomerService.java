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


    public boolean loginCustomer(String email, String password) {


            if (email == null || email.isBlank()) {
               return false;
            }
            if (password == null || password.isBlank()) {
                return false;
            }

            try{
                Customer customer = customerRepository.findByEmail(email);
                String dbPassword = customer.getPhoneNumber(); // provar med telefonnummer som password
                if (!password.equals(dbPassword)) {
                    System.out.println("login failed");
                    return false;
                }
                if (customer == null) {
                    return false;
                }
                activeCustomer = customer;
            }catch(NullPointerException e){

                System.out.println("login failed");
                return false;
            }


        System.out.println("login successful");
            return true;
        }


}
