package com.example.airline.dto.flight;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightResponse {

    private Long id;
    private String flightNumber;
    private String departrue;
    private String arrival;
    private LocalDateTime departrueTime;
    private LocalDateTime arrivalTime;
    private int capacity;
}
