package com.example.airline.dto.reservation;

import lombok.Data;

@Data
public class ReservationResponse {

    private Long id;
    private Long userId;
    private Long flightId;
    private int seatNumber;
}
