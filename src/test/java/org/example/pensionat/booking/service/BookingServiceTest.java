package org.example.pensionat.booking.service;

import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.example.pensionat.room.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void shouldCancelExpiredBookings() {

        Booking booking = new Booking(
                null,
                null,
                LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(1),
                false,
                BookingStatus.ACTIVE
        );

        List<Booking> bookings = List.of(booking);

        //mockito override, när .findAll anropas returnar vi våran egna mock lista
        when(bookingRepository.findAll()).thenReturn(bookings);

        bookingService.updateExpiredBookings();

        assertEquals(BookingStatus.CANCELLED, booking.getStatus());

        verify(bookingRepository).saveAll(bookings);

    }

}
