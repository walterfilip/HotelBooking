package org.example.pensionat.customer.controller;

import jakarta.servlet.http.HttpSession;
import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.service.BookingService;
import org.example.pensionat.customer.CustomerClient;
import org.example.pensionat.customer.dto.*;
import org.example.pensionat.customer.dto.UpdateCustomerRequest;
import org.example.pensionat.customer.model.*;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

//    RestTemplate restTemplate = new RestTemplate();

    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerClient customerClient;

    public CustomerController(BookingService bookingService, RoomService roomService, CustomerClient customerClient) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.customerClient = customerClient;
    }

    @GetMapping
    public String customers(
            @SessionAttribute(value = "customerId", required = false)
            Long customerId, Model model)
    {
        if (customerId == null) {
//            return "redirect:/login";
            return "redirect:/";
        }

        CustomerResponse customer = customerClient.getCustomer(customerId);

        List<Booking> currentBookings = bookingService.getBookingByCustomerId(customerId);
        model.addAttribute(
                "bookings", currentBookings
        );
        model.addAttribute(
                "customer", customer
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
    public String editCustomer(
            @SessionAttribute(value = "customerId", required = false)
            Long customerId,
            @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String phoneNumber,
                               @RequestParam String password,
                               @RequestParam String newPassword,
                               RedirectAttributes redirect) {

        CheckPasswordRequest checkPassword = new CheckPasswordRequest(password, newPassword, customerService.activeCustomer.getEmail());
        Boolean success = restTemplate.postForObject("http://localhost:8081/api/customers/checkpassword", checkPassword, Boolean.class);

        if (Boolean.TRUE.equals(success)) {

            CreateCustomerRequest request = new CreateCustomerRequest(
                    firstName,
                    lastName,
                    customerService.activeCustomer.getEmail(),
                    phoneNumber,
                    newPassword
            );
            UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(request,true);
            Customer update = restTemplate.postForObject("http://localhost:8081/api/customers/update", updateCustomerRequest, Customer.class);

            customerService.activeCustomer = update;

            redirect.addFlashAttribute("message", "Profilen uppdaterad och lösenord ändrat");
            redirect.addFlashAttribute("color", "success");

        } else if (Boolean.FALSE.equals(success) && customerService.emptyCheck(password, newPassword)) {

            CreateCustomerRequest request = new CreateCustomerRequest(
                    firstName,
                    lastName,
                    customerService.activeCustomer.getEmail(),
                    phoneNumber,
                    customerService.activeCustomer.getPassword()
            );
            UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(request, false);
            Customer update = restTemplate.postForObject("http://localhost:8081/api/customers/update", updateCustomerRequest, Customer.class);

            customerService.activeCustomer = update;
            customerClient.updateCustomer(customerId, updateCustomerRequest);

            redirect.addFlashAttribute("message", "Profilen uppdaterad");
            redirect.addFlashAttribute("color", "success");

        }
         else {
            redirect.addFlashAttribute("message", "Profilen uppdaterades inte, försök igen");
            redirect.addFlashAttribute("color", "error");
        }
        return "redirect:/customers/edit";
    }

//    @PostMapping("/edit")
//    public String editCustomer
//            (@SessionAttribute(value = "customerId", required = false)
//             Long customerId,
//             @RequestParam String firstName,
//             @RequestParam String lastName,
//             @RequestParam String phoneNumber,
//             RedirectAttributes redirect
//            ) {
//
//        if (customerId == null) {
//            return "redirect:/";
//        }
//
//        UpdateCustomerRequest request = new UpdateCustomerRequest(
//                firstName,
//                lastName,
//                phoneNumber
//        );
//        customerClient.updateCustomer(customerId, request);
//
//        redirect.addFlashAttribute("message", "Profilen uppdaterad");
//        redirect.addFlashAttribute("color", "success");
//
//        return "redirect:/customers/edit";
//    }

    @GetMapping("/edit")
    public String showEditCustomer(@SessionAttribute
           (value = "customerId", required = false)Long customerId, Model model) {

        if (customerId == null) {
            return "redirect:/";
        }

        CustomerResponse customer = customerClient.getCustomer(customerId);
        model.addAttribute("customer", customer);

        return "customer-edit";
    }

    @PostMapping
    public String createCustomer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password,
            HttpSession session
    ) {

        CreateCustomerRequest request = new CreateCustomerRequest(
                firstName,
                lastName,
                email,
                phoneNumber,
                password
        );

        CustomerResponse customer = customerClient.createCustomer(request);

        session.setAttribute("customerId", customer.id());

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

            HttpSession session,
            Model model

    ) {

        CreateCustomerRequest request = new CreateCustomerRequest(
                firstName,
                lastName,
                email,
                phoneNumber,
                password
        );

        CustomerResponse customer = customerClient.createCustomer(request);

        session.setAttribute("customerId", customer.id());

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
        LoginRequest request = new LoginRequest(email, password);

    try {
        ResponseEntity<Customer> response = restTemplate.postForEntity(
                "http://localhost:8081/api/customers/login",
                request,
                Customer.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            Customer customer = response.getBody();
            customerService.activeCustomer = customer;
            return "redirect:/customers";
        }
//        if(response.getStatusCode() ==  HttpStatus.UNAUTHORIZED) {
//            model.addAttribute("loginError", "Fel användarnman eller lösen");
//            model.addAttribute("title", "Välkommen till Hotellbokning");
//            model.addAttribute("subtitle", "Sök lediga rum och boka");
//            return "index";
//        }
    } catch (HttpClientErrorException.Unauthorized e) {
        model.addAttribute("loginError", "Fel användarnman eller lösen");
        model.addAttribute("title", "Välkommen till Hotellbokning");
        model.addAttribute("subtitle", "Sök lediga rum och boka");
        return "index";

    } catch (ResourceAccessException e) {
        model.addAttribute("loginError", "Tjänsten ligger nere för tillfället");
        model.addAttribute("title", "Välkommen till Hotellbokning");
        model.addAttribute("subtitle", "Sök lediga rum och boka");
        return "index";
    }
    return "redirect:/";
}


//    @PostMapping("/login")
//    public String login(
//            @RequestParam String email,
//            @RequestParam String password,
//            HttpSession session,
//            Model model
//    ) {
//        LoginRequest request = new LoginRequest(email, password);
//
//        CustomerResponse customer = customerClient.login(request);
//
//        if (customer == null) {
//            model.addAttribute("loginError", "Fel användarnman eller lösen");
//            model.addAttribute("title", "Välkommen till Hotellbokning");
//            model.addAttribute("subtitle", "Sök lediga rum och boka");
//            return "index";
//        }
//
//        session.setAttribute("customerId", customer.id());
//
//        return "redirect:/customers";
//    }


    @PostMapping("/delete")
    public String deleteCustomerFromApi(Model model) {
        boolean hasActiveBooking = customerService.checkIfActiveCustomerHasActiveBookings(customerService.activeCustomer);

        if (!hasActiveBooking) {
            Customer customerToDelete = customerService.activeCustomer;
            restTemplate.postForObject("http://localhost:8081/api/customers/delete", customerToDelete, String.class);
            customerService.activeCustomer = null;


            model.addAttribute("successMessage", "Ditt konto har raderats");
            model.addAttribute("title", "Välkommen till Hotellbokning");
            model.addAttribute("subtitle", "Sök lediga rum och boka");
            model.addAttribute("activeCustomer", customerService.activeCustomer);
            return "index";
        }else {
            Customer active = customerService.activeCustomer;
            List<Booking> currentBookings = bookingService.getBookingByCustomerId(active.getId());

            model.addAttribute("customer", active);
            model.addAttribute("bookings", currentBookings);
            model.addAttribute("activeStatus", BookingStatus.ACTIVE);
            model.addAttribute("deleteError", "Du har aktiva bokningar, du kan inte radera ditt konto");

            return "customers";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}




