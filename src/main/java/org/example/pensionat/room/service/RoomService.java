package org.example.pensionat.room.service;


import org.example.pensionat.error.NotFoundException;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.repository.RoomRepository;
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
