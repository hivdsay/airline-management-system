package com.example.airline.exception.flight;

public class FlightCapacityException extends RuntimeException {
    public FlightCapacityException(String message) {
        super(message);
    }
}