package org.example.pensionat.customer.service;


import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.CreateCustomerRequest;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.example.pensionat.room.utils.encoder.Encoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
//    private final BookingRepository bookingRepository;
    public Customer activeCustomer;

    public CustomerService(CustomerRepository customerRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;
//        this.bookingRepository = bookingRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = new  Customer(request.firstName(),request.lastName(),request.email(),request.phoneNumber(),Encoder.hashPassword(request.password()));
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
                if (customer == null) {
                    return false;
                }

                String dbPassword = customer.getPassword();

                if (!Encoder.checkPassword(password, dbPassword)) {
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


    public void updateProfile(CreateCustomerRequest request, boolean changePassword) {
        Customer customer = customerRepository.findByEmail(request.email());
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setPhoneNumber(request.phoneNumber());
        if (changePassword) {
            customer.setPassword(Encoder.hashPassword(request.password()));
        }else {
            customer.setPassword(request.password());
        }

        customerRepository.save(customer);

        activeCustomer = customer;
    }


    public boolean checkPassword(String password, String newPassword) {

        if (password == null || password.isBlank()) {
         return false;

        }
        if (newPassword == null || newPassword.isBlank()) {
            return false;

        }
        Customer customer = customerRepository.findByEmail(activeCustomer.getEmail());
        if (!Encoder.checkPassword(password, customer.getPassword())) {
            System.out.println(customer.getPassword() + " " +  password);
            throw new IllegalArgumentException("passwords don't match");
        }
        return true;
    }
}
