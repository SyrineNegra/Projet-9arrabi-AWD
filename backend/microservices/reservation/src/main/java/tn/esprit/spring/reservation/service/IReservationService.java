package tn.esprit.spring.reservation.service;

import tn.esprit.spring.reservation.dto.ReservationRequestDto;
import tn.esprit.spring.reservation.dto.ReservationResponseDto;

import java.util.List;

public interface IReservationService {

    List<ReservationResponseDto> getReservationsByService(Long serviceId);

    ReservationResponseDto getReservationByIdAndService(Long serviceId, Long reservationId);

    List<ReservationResponseDto> getAllReservations();

    ReservationResponseDto createReservation(Long serviceId, ReservationRequestDto request);

    ReservationResponseDto updateReservation(Long serviceId, Long reservationId, ReservationRequestDto request);

    void deleteReservation(Long serviceId, Long reservationId);

    ReservationResponseDto confirmerReservation(Long serviceId, Long reservationId);

    ReservationResponseDto annulerReservation(Long serviceId, Long reservationId);
}
