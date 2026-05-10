package org.example.hotelbooking.booking.repository;

import org.example.hotelbooking.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
}
