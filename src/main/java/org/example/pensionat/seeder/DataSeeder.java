package org.example.pensionat.seeder;

import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.Customer;
//import org.example.pensionat.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.example.pensionat.room.RoomType;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.repository.RoomRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public DataSeeder(RoomRepository roomRepository,
                      BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) {
        Room firstRoom = null;
        Customer savedCustomer = null;

        if (roomRepository.count() == 0) {
            firstRoom = roomRepository.save(new Room(RoomType.SINGLE, "101", "Utan fönster", 500));
            roomRepository.save(new Room(RoomType.SINGLE, "102", "Havsutsikt", 600));
            roomRepository.save(new Room(RoomType.SINGLE, "103", "Balkong", 600));
            roomRepository.save(new Room(RoomType.SINGLE, "104", "Källarrum", 650));
            roomRepository.save(new Room(RoomType.SINGLE, "105", "Djungelrummet", 700));
            roomRepository.save(new Room(RoomType.SINGLE, "106", "Deluxe rum", 750));

            roomRepository.save(new Room(RoomType.DOUBLE, "107", "Familjerum", 800));
            roomRepository.save(new Room(RoomType.DOUBLE, "108", "Deluxe-rum", 1000));
            roomRepository.save(new Room(RoomType.DOUBLE, "109", "Lyxsvit", 1100));
            roomRepository.save(new Room(RoomType.DOUBLE, "110", "Bröllopssvit", 1500));

        } else {
            firstRoom = roomRepository.findById(1L);
        }
    }
}
