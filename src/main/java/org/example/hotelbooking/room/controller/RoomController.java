package org.example.hotelbooking.room.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @GetMapping
    public String rooms(){
        return "rooms";
    }
}
