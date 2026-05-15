package org.example.pensionat.seeder;


import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.example.pensionat.room.RoomType;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.repository.RoomRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;

    public DataSeeder(RoomRepository roomRepository, CustomerRepository customerRepository) {
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args){
        if (roomRepository.count()==0){
            roomRepository.save(new Room(RoomType.SINGLE, "101", 500));
            roomRepository.save(new Room(RoomType.SINGLE, "102", 500));
            roomRepository.save(new Room(RoomType.SINGLE, "103", 500));
            roomRepository.save(new Room(RoomType.DOUBLE, "104", 750));
            roomRepository.save(new Room(RoomType.DOUBLE, "105", 1000));
        }
        if (customerRepository.count()==0){
            customerRepository.save(new Customer("Nils", "Modig", "nils@fakemail.se", "0767777777"));
        }
    }

}
