package com.example.airline.repository;

import com.example.airline.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT COALESCE(SUM(r.seatNumber), 0) FROM Reservation r WHERE r.flight.id = :flightId")
    int sumSeatsByFlightId(Long flightId);
}
