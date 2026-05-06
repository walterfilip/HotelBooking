package org.example.hotelbooking.models;

import lombok.Data;

@Data
public class Room {

    private int id;
    private RoomType roomType;
    private int roomNr;
    private int price;

}


