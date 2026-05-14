package org.example.hotelbooking.booking.repository;

import org.example.hotelbooking.booking.BookingStatus;
import org.example.hotelbooking.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findCustomerIdAndStatus(Long customerId, BookingStatus status);

    List<Booking> findByRoomAndStatus(Long roomId, BookingStatus status);

}
