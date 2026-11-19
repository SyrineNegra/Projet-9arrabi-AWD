package tn.esprit.spring.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.reservation.dto.ReservationRequestDto;
import tn.esprit.spring.reservation.dto.ReservationResponseDto;
import tn.esprit.spring.reservation.service.IReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReservationController {

    private final IReservationService reservationService;

    /**
     * GET /api/services/{id}/reservations
     * 200 OK ou 404 Not found
     */
    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getReservationsByService(@PathVariable("id") Long id) {
        List<ReservationResponseDto> reservations = reservationService.getReservationsByService(id);
        if (reservations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reservations);
    }

    /**
     * GET /api/services/{id}/reservations/{reservationId}
     * 200 OK ou 404 Not found
     */
    @GetMapping("/{id}/reservations/{reservationId}")
    public ResponseEntity<ReservationResponseDto> getReservationById(
            @PathVariable("id") Long id,
            @PathVariable("reservationId") Long reservationId) {
        ReservationResponseDto reservation = reservationService.getReservationByIdAndService(id, reservationId);
        return ResponseEntity.ok(reservation);
    }

    /**
     * POST /api/services/{id}/reservations
     * 201 Created ou 400 Bad Request
     */
    @PostMapping("/{id}/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @PathVariable("id") Long id,
            @Valid @RequestBody ReservationRequestDto request) {
        ReservationResponseDto created = reservationService.createReservation(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/services/{id}/reservations
     * 200 OK ou 404 Not found
     * (Accepte l'ID dans le corps de la requête ou en paramètre)
     */
    @PutMapping("/{id}/reservations")
    public ResponseEntity<ReservationResponseDto> updateReservation(
            @PathVariable("id") Long id,
            @RequestParam(value = "reservationId", required = false) Long reservationIdParam,
            @Valid @RequestBody ReservationRequestDto request) {
        Long targetId = reservationIdParam != null ? reservationIdParam : request.getId();
        ReservationResponseDto updated = reservationService.updateReservation(id, targetId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * PUT /api/services/{id}/reservations/{reservationId}
     * 200 OK ou 404 Not found
     */
    @PutMapping("/{id}/reservations/{reservationId}")
    public ResponseEntity<ReservationResponseDto> updateReservationWithPathId(
            @PathVariable("id") Long id,
            @PathVariable("reservationId") Long reservationId,
            @Valid @RequestBody ReservationRequestDto request) {
        ReservationResponseDto updated = reservationService.updateReservation(id, reservationId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/services/{id}/reservations/supprimer
     * 204 No Content
     * (Accepte le reservationId en paramètre ou via corps JSON)
     */
    @DeleteMapping("/{id}/reservations/supprimer")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable("id") Long id,
            @RequestParam(value = "reservationId", required = false) Long reservationIdParam,
            @RequestBody(required = false) ReservationRequestDto body) {
        Long targetId = reservationIdParam != null ? reservationIdParam : (body != null ? body.getId() : null);
        reservationService.deleteReservation(id, targetId);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/services/{id}/reservations/supprimer/{reservationId}
     * 204 No Content
     */
    @DeleteMapping("/{id}/reservations/supprimer/{reservationId}")
    public ResponseEntity<Void> deleteReservationWithPath(
            @PathVariable("id") Long id,
            @PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(id, reservationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Confirmer une réservation
     * PUT ou PATCH /api/services/{id}/reservations/{reservationId}/confirmer
     */
    @PatchMapping("/{id}/reservations/{reservationId}/confirmer")
    public ResponseEntity<ReservationResponseDto> confirmerReservation(
            @PathVariable("id") Long id,
            @PathVariable("reservationId") Long reservationId) {
        ReservationResponseDto confirmed = reservationService.confirmerReservation(id, reservationId);
        return ResponseEntity.ok(confirmed);
    }

    /**
     * Annuler une réservation
     * PUT ou PATCH /api/services/{id}/reservations/{reservationId}/annuler
     */
    @PatchMapping("/{id}/reservations/{reservationId}/annuler")
    public ResponseEntity<ReservationResponseDto> annulerReservation(
            @PathVariable("id") Long id,
            @PathVariable("reservationId") Long reservationId) {
        ReservationResponseDto cancelled = reservationService.annulerReservation(id, reservationId);
        return ResponseEntity.ok(cancelled);
    }
}
