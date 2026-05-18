package org.example.pensionat.room.repository;

import org.example.pensionat.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
    int price(int price);

    public Room findById(int id);
}
