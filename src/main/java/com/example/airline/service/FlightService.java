package com.example.airline.service;

import com.example.airline.dto.flight.CreateFlightRequest;
import com.example.airline.dto.flight.FlightResponse;
import com.example.airline.dto.flight.UpdateFlightRequest;
import com.example.airline.entity.Flight;
import com.example.airline.exception.flight.FlightNotFoundException;
import com.example.airline.mapper.FlightMapper;
import com.example.airline.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    // CREATE
    @Transactional
    public FlightResponse createFlight(CreateFlightRequest request) {

        if (request.getDepartrueTime().isAfter(request.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }

        Flight flight = flightMapper.toEntity(request);
        Flight saved = flightRepository.save(flight);

        logger.info("Flight created | id={} | number={}", saved.getId(), saved.getFlightNumber());

        return flightMapper.toResponse(saved);
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException(id));

        return flightMapper.toResponse(flight);
    }

    // GET ALL
    @Transactional(readOnly = true)
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(flightMapper::toResponse)
                .toList();
    }

    // UPDATE
    @Transactional
    public FlightResponse updateFlight(Long id, UpdateFlightRequest request) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException(id));

        // İş kuralı
        if (request.getDepartrueTime() != null &&
            request.getArrivalTime() != null &&
            request.getDepartrueTime().isAfter(request.getArrivalTime())) {

            throw new IllegalArgumentException("Departure time must be before arrival time");
        }

        // Entity güncelleme mapper ile
        flightMapper.updateEntity(flight, request);

        // save (dirty checking ile otomatik)
        Flight saved = flightRepository.save(flight);

        logger.info("Flight updated | id={} | number={}", saved.getId(), saved.getFlightNumber());

        return flightMapper.toResponse(saved);
    }

    // DELETE
    @Transactional
    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException(id));

        flightRepository.delete(flight);

        logger.info("Flight deleted | id={}", id);
    }
}
