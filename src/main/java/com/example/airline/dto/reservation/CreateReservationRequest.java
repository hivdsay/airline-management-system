package com.example.airline.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReservationRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long flightId;

    // tek koltukluk sistem kullanıyoruz. Request içinde koltuk sayısı belirtilmese bile sistem düzgün çalışır
    private int seatNumber = 1;
}
