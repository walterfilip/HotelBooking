package org.example.hotelbooking.room.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.hotelbooking.room.RoomType;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Rumstyp måste anges")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @NotBlank(message = "Rumsnummer måste anges")
    private int roomNr;

    @Min(value = 1, message = "Pris per natt måste vara större än 0")
    private int price;

    protected Room() {
    }

    public Room(RoomType roomType, int roomNr, int price) {
        this.roomType = roomType;
        this.roomNr = roomNr;
        this.price = price;
    }

}


