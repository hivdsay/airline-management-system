package com.example.airline.exception.flight;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(Long id) {
        super("Flight not found: " + id);
    }
}

