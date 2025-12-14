package com.example.airline.dto.flight;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateFlightRequest {

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String departrue;

    @NotBlank
    private String arrival;

    @NotNull
    private LocalDateTime departrueTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    @Min(1)
    private Integer capacity;
}
