package org.example.pensionat.customer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.service.BookingService;
import org.example.pensionat.customer.client.CustomerClient;
import org.example.pensionat.customer.model.*;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.service.RoomService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerClient customerClient;
    private final SecurityContextRepository securityContextRepository;

    public CustomerController(BookingService bookingService,
                              RoomService roomService,
                              CustomerClient customerClient,
                              SecurityContextRepository securityContextRepository
    ) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.customerClient = customerClient;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping
    public String customers(
            @SessionAttribute(value = "customerId", required = false) Long customerId,
            Model model,
            RedirectAttributes redirect,
            Authentication authentication) {
        System.out.println("Authentication: " + authentication.isAuthenticated());
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("Authorities: " + authentication.getAuthorities());


        if (customerId == null) {

            return "redirect:/";
        }

        try {
            CustomerResponse customer = customerClient.getCustomer(customerId);

            List<Booking> currentBookings = bookingService.getBookingByCustomerId(customerId);
            model.addAttribute("bookings", currentBookings);
            model.addAttribute("customer", customer);
            model.addAttribute("activeStatus", BookingStatus.ACTIVE);

            return "customers";
        }catch (ResourceAccessException e) {
            redirect.addFlashAttribute("title", "Välkommen till Hotellbokning");
            redirect.addFlashAttribute("subtitle", "Sök lediga rum och boka");
            redirect.addFlashAttribute("loginError", "Tjänsten ligger nere för tillfället");


            return "redirect:/";
        }
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
            RedirectAttributes redirect,
            Model model
    ) {

        if (customerId == null) {
            return "redirect:/";
        }

        try {
            CustomerResponse customer = customerClient.getCustomer(customerId);

            CheckPasswordRequest checkPassword = new CheckPasswordRequest(password, newPassword, customer.email());

            Boolean success = customerClient.checkPassword(checkPassword);

            if (Boolean.TRUE.equals(success)) {
                UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(
                        firstName,
                        lastName,
                        phoneNumber,
                        newPassword,
                        true
                );

                customerClient.updateCustomer(customerId, updateCustomerRequest);

                redirect.addFlashAttribute("message", "Profilen uppdaterad och lösenord ändrat");
                redirect.addFlashAttribute("color", "success");

            } else if (Boolean.FALSE.equals(success) && emptyCheck(password, newPassword)) {

                UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(
                        firstName,
                        lastName,
                        phoneNumber,
                        null,
                        false
                );

                customerClient.updateCustomer(customerId, updateCustomerRequest);

                redirect.addFlashAttribute("message", "Profilen uppdaterad");
                redirect.addFlashAttribute("color", "success");

            } else {
                redirect.addFlashAttribute("message", "Profilen uppdaterades inte, försök igen");
                redirect.addFlashAttribute("color", "error");
            }
            return "redirect:/customers/edit";
        } catch (ResourceAccessException e) {
            model.addAttribute("title", "Välkommen till Hotellbokning");
            model.addAttribute("subtitle", "Sök lediga rum och boka");
            model.addAttribute("loginError", "Tjänsten ligger nere för tillfället");

            return "index";
        }
    }

    @GetMapping("/edit")
    public String showEditCustomer(@SessionAttribute(value = "customerId", required = false) Long customerId, Model model) {
        if (customerId == null) {
            return "redirect:/";
        }

        try {
            CustomerResponse customer = customerClient.getCustomer(customerId);
            model.addAttribute("customer", customer);

            return "customer-edit";
        }catch (ResourceAccessException e) {
            model.addAttribute("title", "Välkommen till Hotellbokning");
            model.addAttribute("subtitle", "Sök lediga rum och boka");
            model.addAttribute("loginError", "Tjänsten ligger nere för tillfället");

            return "index";
        }
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
            @SessionAttribute(value = "customerId", required = false)
            //??
            Long customerId,
            RedirectAttributes redirect,
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

        try {
            CustomerResponse customer = customerClient.createCustomer(request);
            if (customer.id() == null) {
                redirect.addAttribute("roomId", roomId);
                redirect.addAttribute("startDate", startDate);
                redirect.addAttribute("endDate", endDate);
                redirect.addAttribute("extraBed", extraBed);
                redirect.addFlashAttribute("loginError", "E-post är kopplat till ett redan existerande konto");

                return "redirect:/customers/form";
            }

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
        } catch (ResourceAccessException e) {
            model.addAttribute("title", "Välkommen till Hotellbokning");
            model.addAttribute("subtitle", "Sök lediga rum och boka");
            model.addAttribute("loginError", "Tjänsten ligger nere för tillfället");

            return "index";
        }
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            model.addAttribute("title", "Välkommen till Hotellbokning");
            model.addAttribute("subtitle", "Sök lediga rum och boka");
            model.addAttribute("loginError", "Fyll i användarnamn och lösenord");
            return "index";
        }

        LoginRequest request = new LoginRequest(email, password);

        try {
            CustomerResponse customer = customerClient.login(request);

            session.setAttribute("customerId", customer.id());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    customer.id(),
                    null,
                    List.of()
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            securityContextRepository.saveContext(
                    context,
                    httpRequest,
                    httpResponse
            );


            return "redirect:/customers";

        } catch (HttpClientErrorException.Unauthorized e) {
            model.addAttribute("title", "Välkommen till Hotellbokning");
            model.addAttribute("subtitle", "Sök lediga rum och boka");
            model.addAttribute("loginError", "Fel användarnamn eller lösen");

            return "index";

        } catch (ResourceAccessException e) {
            model.addAttribute("title", "Välkommen till Hotellbokning");
            model.addAttribute("subtitle", "Sök lediga rum och boka");
            model.addAttribute("loginError", "Tjänsten ligger nere för tillfället");

            return "index";
        }
    }

    @PostMapping("/delete")
    public String deleteCustomer(
            Model model,
            @SessionAttribute(value = "customerId", required = false)
            Long customerId,
            HttpSession session
    ) {
        if (customerId == null) {
            return "redirect:/";
        }

        boolean hasActiveBooking = checkIfActiveCustomerHasActiveBookings(customerId);

        if (!hasActiveBooking) {
            try {
                customerClient.deleteCustomer(customerId);
            } catch (ResourceAccessException e) {
                model.addAttribute("title", "Välkommen till Hotellbokning");
                model.addAttribute("subtitle", "Sök lediga rum och boka");
                model.addAttribute("loginError", "Tjänsten ligger nere för tillfället");

                return "index";
            }
            session.setAttribute("customerId", null);
            model.addAttribute("successMessage", "Ditt konto har raderats");
            model.addAttribute("title", "Välkommen till Hotellbokning");
            model.addAttribute("subtitle", "Sök lediga rum och boka");

            return "index";
        } else {
            try {
                CustomerResponse customer = customerClient.getCustomer(customerId);
                List<Booking> currentBookings = bookingService.getBookingByCustomerId(customerId);

                model.addAttribute("customer", customer);
                model.addAttribute("bookings", currentBookings);
                model.addAttribute("activeStatus", BookingStatus.ACTIVE);
                model.addAttribute("deleteError", "Du har aktiva bokningar, du kan inte radera ditt konto");

                return "customers";

            } catch (ResourceAccessException e) {
                model.addAttribute("title", "Välkommen till Hotellbokning");
                model.addAttribute("subtitle", "Sök lediga rum och boka");
                model.addAttribute("loginError", "Tjänsten ligger nere för tillfället");

                return "index";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    public boolean emptyCheck(String password, String newPassword) {
        if (password == null || password.isBlank()) {
            if (newPassword == null || newPassword.isBlank()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfActiveCustomerHasActiveBookings(Long customerId) {
        return bookingService.checkIfCustomerHasActiveBookings(customerId);
    }
}




