package org.example.pensionat.customer.service;

import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.booking.model.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    private final BookingRepository bookingRepository;
    public Customer activeCustomer;

    public CustomerService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }



    public boolean emptyCheck(String password, String newPassword) {
        if (password == null || password.isBlank()) {
            if (newPassword == null || newPassword.isBlank()) {
                return true;
            }

        }
//        if (newPassword == null || newPassword.isBlank()) {
//            return true;
//        }
        return false;
    }
    public boolean checkIfActiveCustomerHasActiveBookings(Customer customer) {
//        Customer customer = activeCustomer;

        if (customer == null) {
            return false;
        }
        boolean hasActiveBookings = bookingRepository
                .existsByCustomerIdAndStatus(customer.getId(), BookingStatus.ACTIVE);

        if (hasActiveBookings) {
            return true;
        }
        List<Booking> bookings = bookingRepository.findByCustomerId(customer.getId());
        bookingRepository.deleteAll(bookings);
        return false;
    }
}
