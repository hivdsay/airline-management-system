package com.example.airline.mapper;


import com.example.airline.dto.reservation.CreateReservationRequest;
import com.example.airline.dto.reservation.ReservationResponse;
import com.example.airline.dto.reservation.UpdateReservationRequest;
import com.example.airline.entity.Flight;
import com.example.airline.entity.Reservation;
import com.example.airline.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation toEntity(CreateReservationRequest request, User user, Flight flight) {
        Reservation r = new Reservation();
        r.setUser(user);
        r.setFlight(flight);
        r.setSeatNumber(request.getSeatNumber());
        return r;
    }

    public void updateEntity(Reservation r, UpdateReservationRequest request) {
        if (request.getSeatNumber() != null)
            r.setSeatNumber(request.getSeatNumber());
    }

    public ReservationResponse toResponse(Reservation r){
        ReservationResponse dto = new ReservationResponse();
        dto.setId(r.getId());
        dto.setFlightId(r.getFlight().getId());
        dto.setUserId(r.getUser().getId());
        dto.setSeatNumber(r.getSeatNumber());
        return dto;
    }
}
