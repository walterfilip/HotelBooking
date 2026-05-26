package org.example.pensionat.seeder;


import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.example.pensionat.room.utils.encoder.Encoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.example.pensionat.room.RoomType;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.repository.RoomRepository;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    public DataSeeder(RoomRepository roomRepository, CustomerRepository customerRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args){
        Room firstRoom = null;
        Customer savedCustomer = null;

        if (roomRepository.count()==0){
            firstRoom = roomRepository.save(new Room(RoomType.SINGLE, "101", "Utan fönster", 500));
            roomRepository.save(new Room(RoomType.SINGLE, "102", "Havsutsikt", 600));
            roomRepository.save(new Room(RoomType.SINGLE, "103", "Balkong", 600));
            roomRepository.save(new Room(RoomType.DOUBLE, "104", "Familjerum", 750));
            roomRepository.save(new Room(RoomType.DOUBLE, "105", "Lyxsvit", 1000));
        } else {
            firstRoom = roomRepository.findById(1L);
        }
        if (customerRepository.count()==0){
            savedCustomer = customerRepository.save(new Customer("Nils", "Modig", "nils@fakemail.se", "0767777777", Encoder.hashPassword("hej")));
            customerRepository.save(new Customer("Peter", "Peterstein", "peter@fakemail.se", "0767777776","hej"));

        } else {
            savedCustomer = customerRepository.findByEmail("nils@fakemail.se");
        }
        if (bookingRepository.count()==0){
            if (firstRoom != null && savedCustomer != null) {
                bookingRepository.save(new Booking(savedCustomer, firstRoom, LocalDate.now(), LocalDate.now(), false, BookingStatus.ACTIVE));
            }
        }
    }

}
