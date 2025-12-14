package com.example.airline.mapper;


import com.example.airline.dto.flight.CreateFlightRequest;
import com.example.airline.dto.flight.FlightResponse;
import com.example.airline.dto.flight.UpdateFlightRequest;
import com.example.airline.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public Flight toEntity(CreateFlightRequest request) {
        Flight flight = new Flight();
        flight.setFlightNumber(request.getFlightNumber());
        flight.setDepartrue(request.getDepartrue());
        flight.setArrival(request.getArrival());
        flight.setDepartrueTime(request.getDepartrueTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setCapacity(request.getCapacity());
        return flight;
    }

    public void updateEntity(Flight flight, UpdateFlightRequest request) {
        if (request.getFlightNumber() != null)
            flight.setFlightNumber(request.getFlightNumber());

        if (request.getDepartrue() != null)
            flight.setDepartrue(request.getDepartrue());

        if (request.getArrival() != null)
            flight.setArrival(request.getArrival());

        if (request.getDepartrueTime() != null)
            flight.setDepartrueTime(request.getDepartrueTime());

        if (request.getArrivalTime() != null)
            flight.setArrivalTime(request.getArrivalTime());

        if (request.getCapacity() != null)
            flight.setCapacity(request.getCapacity());
    }

    public FlightResponse toResponse(Flight f) {
        FlightResponse dto = new FlightResponse();
        dto.setId(f.getId());
        dto.setFlightNumber(f.getFlightNumber());
        dto.setDepartrue(f.getDepartrue());
        dto.setArrival(f.getArrival());
        dto.setDepartrueTime(f.getDepartrueTime());
        dto.setArrivalTime(f.getArrivalTime());
        dto.setCapacity(f.getCapacity());
        return dto;
    }
}
