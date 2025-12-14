package com.example.airline.service;

import com.example.airline.dto.reservation.CreateReservationRequest;
import com.example.airline.dto.reservation.ReservationResponse;
import com.example.airline.dto.reservation.UpdateReservationRequest;
import com.example.airline.entity.Flight;
import com.example.airline.entity.Reservation;
import com.example.airline.entity.User;
import com.example.airline.exception.flight.FlightNotFoundException;
import com.example.airline.exception.reservation.ReservationNotFoundException;
import com.example.airline.exception.reservation.SeatNotAvailableException;
import com.example.airline.exception.user.UserNotFoundException;
import com.example.airline.mapper.ReservationMapper;
import com.example.airline.repository.FlightRepository;
import com.example.airline.repository.ReservationRepository;
import com.example.airline.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getUserId()));

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new FlightNotFoundException(request.getFlightId()));


        //Kapasite uygun mu?
        int reservedSeats = reservationRepository.sumSeatsByFlightId(flight.getId());
        int remaining = flight.getCapacity() - reservedSeats;

        if (request.getSeatNumber() > remaining) {
            throw new SeatNotAvailableException(
                    "Not enough seats available. Remaining: " + remaining
            );
        }


        Reservation reservation = reservationMapper.toEntity(request, user, flight);
        Reservation saved = reservationRepository.save(reservation);

        logger.info("Reservation created | id={} | user={} | flight={}",
                saved.getId(), user.getEmail(), flight.getFlightNumber());

        return reservationMapper.toResponse(saved);
    }

    @Transactional
    public ReservationResponse getById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found: " + id));

        return reservationMapper.toResponse(reservation);
    }

    @Transactional
    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Transactional
    public ReservationResponse updateReservation(Long id, UpdateReservationRequest request) {

        //Rezervasyon var mı?
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found: " + id));

        Flight flight = reservation.getFlight();

        //Eğer seats değişiyorsa kapasiteyi kontrol et
        if (request.getSeatNumber() != null) {

            int reservedSeats = reservationRepository.sumSeatsByFlightId(flight.getId());
            int remaining = flight.getCapacity() - (reservedSeats - reservation.getSeatNumber());

            if (request.getSeatNumber() > remaining) {
                throw new SeatNotAvailableException(
                        "Not enough seats available. Remaining: " + remaining
                );
            }

            reservation.setSeatNumber(request.getSeatNumber());
        }

        logger.info("Reservation updated | id={}", reservation.getId());

        return reservationMapper.toResponse(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {

        if (!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException("Cannot delete. Reservation not found: " + id);
        }

        reservationRepository.deleteById(id);

        logger.info("Reservation deleted | id={}", id);
    }
}

