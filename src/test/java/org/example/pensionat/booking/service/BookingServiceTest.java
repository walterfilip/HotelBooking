package org.example.pensionat.booking.service;

import org.example.pensionat.booking.BookingStatus;
import org.example.pensionat.booking.model.Booking;
import org.example.pensionat.booking.model.CreateBookingRequest;
import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.example.pensionat.error.NotFoundException;
import org.example.pensionat.room.model.Room;
import org.example.pensionat.room.repository.RoomRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
    private Customer customer1 = new Customer();
    private Room room1 = new Room();
    private Booking booking1 = new Booking();
    private Booking booking2 = new Booking();
    private List<Booking> bookingList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        customer1 = new Customer(
        );
        room1 = new Room();
        booking1 = new Booking(
                customer1,
                room1,
                LocalDate.now(),
                LocalDate.now().plusDays(4),
                false,
                BookingStatus.ACTIVE
        );
        booking2 = new Booking(
                customer1,
                room1,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(6),
                true,
                BookingStatus.CANCELLED
        );
        bookingList = List.of(booking1, booking2);

    }

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

    @Test
    void getAllBookings() {

        when(bookingRepository.findAll()).thenReturn(bookingList);
        List<Booking> results = bookingService.getAllBookings();
        assertNotNull(results);
        assertEquals(2, results.size());
        assertThat(results, Matchers.contains(booking1, booking2));

        verify(bookingRepository).findAll();
    }

    @Test
    void throwExceptionWhenDateOverlap() {

        CreateBookingRequest request = new CreateBookingRequest(
                1L,
                1L,
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(3),
                false
        );
        assertThrows(NotFoundException.class, () -> bookingService.createBooking(request));
    }
}