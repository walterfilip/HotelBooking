package org.example.pensionat.room.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.example.hotelbooking.room.RoomType;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @GetMapping
    public String rooms(){
        return "rooms";
    }

    @GetMapping("/search")
    public String searchRooms (
     @RequestParam String startDate,
     @RequestParam String endDate,
     @RequestParam RoomType roomType,
     Model model) {


        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("roomType", roomType);

        return "rooms";
    }



}

