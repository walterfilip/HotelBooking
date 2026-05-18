package org.example.pensionat.booking.service;


import jakarta.transaction.Transactional;
import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.model.CreateBookingRequest;
import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.example.pensionat.error.BadRequestException;
import org.example.pensionat.error.NotFoundException;
import org.example.pensionat.room.RoomType;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.example.pensionat.room.utils.Validations.validateDateRange;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


    @Transactional
    public Booking createBooking(CreateBookingRequest request) {

        Customer customer = customerRepository.findById(request.customerId()).orElseThrow(() -> new NotFoundException("Kunden finns inte"));

        Room room = roomRepository.findById(request.roomId()).orElseThrow(() -> new NotFoundException("Rummet finns inte"));

        validateDateRange(request.startDate(), request.endDate());
        validateRoomAvailability(request.roomId(), request.startDate(),request.endDate(), null);
        validateExtraBed(room, request.extraBed());

        Booking booking = new Booking(customer, room,request.startDate(),request.endDate(), request.extraBed(), BookingStatus.ACTIVE);

        return bookingRepository.save(booking);

    }

    @Transactional
    public Booking cancelBooking(Long bookingId){

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking finns inte"));

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    private void validateRoomAvailability(Long roomId, LocalDate start, LocalDate end, Long bookingIdToIgnore) {
        List<Booking> bookings = bookingRepository.findByRoom_IdAndStatus(roomId, BookingStatus.ACTIVE);

        for(Booking existingBooking: bookings){
            if(bookingIdToIgnore != null && bookingIdToIgnore.equals(existingBooking.getId())){
                continue;
            }

            boolean overlap = !start.isAfter(existingBooking.getEndDate()) && !end.isBefore(existingBooking.getStartDate());

            if(overlap){
                throw new BadRequestException("Rummet är redan bokat under valt datum");
            }
        }
    }

    private void validateExtraBed(Room room, boolean extraBedRequested){

        if(extraBedRequested && room.getRoomType() != RoomType.DOUBLE){
            throw new BadRequestException(
                    "Detta rum stödjer inte extrasäng"
            );
        }
    }
}
