package org.example.pensionat.room.controller;

import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.example.pensionat.room.RoomType;
import org.springframework.ui.Model;
import java.util.List;
import java.time.LocalDate;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
    this.roomService = roomService;
    }

    @GetMapping
    public String rooms(Model model) {
    model.addAttribute("rooms", roomService.getAllRooms());
    return "rooms";
    }
//    @GetMapping("/rooms")
//    public String getRooms(Model model){
//        model.addAttribute("rooms", roomService.getAllRooms());
//        return "rooms";
//
//    }

    @GetMapping("/search")
     public String searchRooms (
     @RequestParam String startDate,
     @RequestParam String endDate,
     @RequestParam RoomType roomType,
     Model model) {

        List<Room> availableRooms = roomService.getAvailableRooms(
         roomType,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );

        model.addAttribute("rooms", availableRooms);

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("roomType", roomType);


        return "rooms";
    }



}

