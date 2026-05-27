package org.example.pensionat.customer.controller;

import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.service.BookingService;
import org.example.pensionat.customer.model.CreateCustomerRequest;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.service.CustomerService;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.service.RoomService;
import org.example.pensionat.room.utils.encoder.Encoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/customers")
public class CustomerController {

   private final CustomerService customerService;
   private final BookingService bookingService;
   private final RoomService roomService;

   public CustomerController(CustomerService customerService, BookingService bookingService, RoomService roomService) {
       this.customerService = customerService;
       this.bookingService = bookingService;
       this.roomService = roomService;
   }

    @GetMapping
    public String customers(Model model) {
        Customer active = customerService.activeCustomer;
        List<Booking> currentBookings = bookingService.getBookingByCustomerId(active.getId());
        model.addAttribute(
                "bookings", currentBookings
        );
        model.addAttribute(
                "customer", active
        );
        model.addAttribute("activeStatus",
                BookingStatus.ACTIVE
        );

        return "customers";
    }



    @GetMapping("/form")
    public String showCustomerForm(

             @RequestParam Long roomId,
             @RequestParam String startDate,
             @RequestParam String endDate,
             @RequestParam(defaultValue = "false") boolean extraBed,

             Model model
    ) {

       model.addAttribute("roomId", roomId);
       model.addAttribute("startDate", startDate);
       model.addAttribute("endDate", endDate);
       model.addAttribute("extraBed", extraBed);

        return "customer-form";
    }

    @PostMapping("/edit")
    public String editCustomer(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String phoneNumber,
                               @RequestParam String password,
                               @RequestParam String newPassword,
                               RedirectAttributes redirect ){

        if (customerService.checkPassword(password,newPassword)){

            CreateCustomerRequest request = new CreateCustomerRequest(
                    firstName,
                    lastName,
                    customerService.activeCustomer.getEmail(),
                    phoneNumber,
                    newPassword
            );
            customerService.updateProfile(request, true);
            redirect.addFlashAttribute("message", "Profilen uppdaterad och lösenord ändrat");
            redirect.addFlashAttribute("color", "success");

        }else if (password.isBlank() && newPassword.isBlank())    {

            CreateCustomerRequest request = new CreateCustomerRequest(
                    firstName,
                    lastName,
                    customerService.activeCustomer.getEmail(),
                    phoneNumber,
                    customerService.activeCustomer.getPassword()
            );
            customerService.updateProfile(request, false);
            redirect.addFlashAttribute("message", "Profilen uppdaterad");
            redirect.addFlashAttribute("color", "success");

        } else {
            redirect.addFlashAttribute("message", "Profilen uppdaterades inte, försök igen");
            redirect.addFlashAttribute("color", "error");
        }
        return "redirect:/customers/edit";
    }

    @GetMapping("/edit")
    public String editCustomer(Model model) {
        customerService.activeCustomer = customerService.getCustomerById(customerService.activeCustomer.getId());

        model.addAttribute("customer", customerService.activeCustomer);
        return "customer-edit";
    }

    @PostMapping
    public String createCustomer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password
    ) {

        CreateCustomerRequest request = new CreateCustomerRequest(
                firstName,
                lastName,
                email,
                phoneNumber,
                Encoder.hashPassword(password)
        );

        customerService.createCustomer(request);

       return "redirect:/customers";
    }

    @PostMapping("/booking")
    public String createCustomerWhileBooking(

            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password,

            @RequestParam Long roomId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam boolean extraBed,

            Model model

    ){

        CreateCustomerRequest request = new CreateCustomerRequest(
                firstName,
                lastName,
                email,
                phoneNumber,
                password
        );

        Customer customer = customerService.createCustomer(request);

        customerService.activeCustomer = customer;

        Room room = roomService.getRoomById(roomId);

        int totalPrice = bookingService.getTotalPrice(
                room,
                java.time.LocalDate.parse(startDate),
                java.time.LocalDate.parse(endDate),
                extraBed
        );

        model.addAttribute("customer", customer);
        model.addAttribute("room", room);

        model.addAttribute("roomId", roomId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("extraBed", extraBed);
        model.addAttribute("totalPrice", totalPrice);

        return "booking-form";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        if (!customerService.loginCustomer(email, password)) {
            model.addAttribute("loginError", "Invalid username and/or password");
            return "index";
        }
        return "redirect:/customers";
    }

    @GetMapping("/logout")
    public String logout() {
        customerService.activeCustomer = null;
        return "redirect:/";
    }

}




