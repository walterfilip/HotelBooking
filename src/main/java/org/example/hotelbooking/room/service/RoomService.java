package org.example.hotelbooking.room.service;


import org.example.hotelbooking.error.NotFoundException;
import org.example.hotelbooking.room.model.Room;
import org.example.hotelbooking.room.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> getAllRooms(){
        return repository.findAll();
    }

    public Room getRoomById(Long id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Kunde ej hitta rummet angivet"));
    }
}
