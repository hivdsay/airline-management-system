package com.example.airline.controller;

import com.example.airline.security.SwaggerResponses.*;
import com.example.airline.dto.flight.CreateFlightRequest;
import com.example.airline.dto.flight.FlightResponse;
import com.example.airline.dto.flight.UpdateFlightRequest;
import com.example.airline.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Tag(name = "Flight Management", description = "API endpoints for flight operations")
@SecurityRequirement(name = "Bearer Authentication")
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @Operation(summary = "Create new flight", description = "Creates a new flight record in the system")
    @CreateApiResponses
    public ResponseEntity<FlightResponse> create(@Valid @RequestBody CreateFlightRequest request) {
        return ResponseEntity.ok(flightService.createFlight(request));
    }

    @GetMapping
    @Operation(summary = "List all flights", description = "Retrieves all flights from the system")
    @GetApiResponses
    public ResponseEntity<List<FlightResponse>> getAll() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get flight details", description = "Returns details of a specific flight by ID")
    @GetByIdApiResponses
    public ResponseEntity<FlightResponse> getById(
            @Parameter(description = "Flight ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update flight information", description = "Updates information of an existing flight")
    @UpdateApiResponses
    public ResponseEntity<FlightResponse> update(
            @Parameter(description = "Flight ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateFlightRequest request) {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete flight", description = "Permanently deletes a flight from the system")
    @DeleteApiResponses
    public ResponseEntity<Void> delete(
            @Parameter(description = "Flight ID to delete", example = "1")
            @PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}