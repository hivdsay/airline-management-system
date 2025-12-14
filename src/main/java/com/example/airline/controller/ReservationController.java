package com.example.airline.controller;

import com.example.airline.security.SwaggerResponses.*;
import com.example.airline.dto.reservation.CreateReservationRequest;
import com.example.airline.dto.reservation.ReservationResponse;
import com.example.airline.dto.reservation.UpdateReservationRequest;
import com.example.airline.service.ReservationService;
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
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservation Management", description = "API endpoints for reservation operations")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(summary = "Create new reservation", description = "Creates a new reservation record for the specified flight")
    @CreateApiResponses
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody CreateReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @GetMapping
    @Operation(summary = "List all reservations", description = "Retrieves all reservations from the system")
    @GetApiResponses
    public ResponseEntity<List<ReservationResponse>> getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation details", description = "Returns details of a specific reservation by ID")
    @GetByIdApiResponses
    public ResponseEntity<ReservationResponse> getById(
            @Parameter(description = "Reservation ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reservation information", description = "Updates information of an existing reservation")
    @UpdateApiResponses
    public ResponseEntity<ReservationResponse> update(
            @Parameter(description = "Reservation ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateReservationRequest request) {
        return ResponseEntity.ok(reservationService.updateReservation(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel reservation", description = "Permanently deletes a reservation from the system")
    @DeleteApiResponses
    public ResponseEntity<Void> delete(
            @Parameter(description = "Reservation ID to cancel", example = "1")
            @PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}