package tn.esprit.spring.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.reservation.dto.CreneauDto;
import tn.esprit.spring.reservation.dto.ReservationRequestDto;
import tn.esprit.spring.reservation.dto.ReservationResponseDto;
import tn.esprit.spring.reservation.entity.Creneau;
import tn.esprit.spring.reservation.entity.Reservation;
import tn.esprit.spring.reservation.entity.StatutReservation;
import tn.esprit.spring.reservation.exception.BadRequestException;
import tn.esprit.spring.reservation.exception.ResourceNotFoundException;
import tn.esprit.spring.reservation.repository.CreneauRepository;
import tn.esprit.spring.reservation.repository.ReservationRepository;
import tn.esprit.spring.reservation.service.IReservationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final CreneauRepository creneauRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getReservationsByService(Long serviceId) {
        List<Reservation> reservations = reservationRepository.findByServiceId(serviceId);
        return reservations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDto getReservationByIdAndService(Long serviceId, Long reservationId) {
        Reservation reservation = reservationRepository.findByIdAndServiceId(reservationId, serviceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Réservation introuvable avec l'ID: " + reservationId + " pour le service: " + serviceId));
        return mapToResponse(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponseDto createReservation(Long serviceId, ReservationRequestDto request) {
        if (serviceId == null) {
            throw new BadRequestException("L'identifiant du service est obligatoire.");
        }

        Creneau creneau = null;
        if (request.getCreneauId() != null) {
            creneau = creneauRepository.findById(request.getCreneauId())
                    .orElseThrow(() -> new ResourceNotFoundException("Créneau introuvable avec l'ID: " + request.getCreneauId()));

            if (!creneau.verifierDisponibilite()) {
                throw new BadRequestException("Le créneau demandé n'est plus disponible.");
            }
        } else if (request.getCreneau() != null) {
            CreneauDto cDto = request.getCreneau();
            creneau = Creneau.builder()
                    .date(cDto.getDate() != null ? cDto.getDate() : request.getDate())
                    .heureDebut(cDto.getHeureDebut())
                    .heureFin(cDto.getHeureFin())
                    .disponible(true)
                    .serviceId(serviceId)
                    .build();
            creneau = creneauRepository.save(creneau);
        }

        Reservation reservation = Reservation.builder()
                .date(request.getDate())
                .description(request.getDescription())
                .serviceId(serviceId)
                .userId(request.getUserId())
                .creneau(creneau)
                .statut(request.getStatut() != null ? request.getStatut() : StatutReservation.EN_ATTENTE.name())
                .build();

        reservation.creerReservation();

        if (creneau != null) {
            creneauRepository.save(creneau);
        }

        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    @Override
    public ReservationResponseDto updateReservation(Long serviceId, Long reservationId, ReservationRequestDto request) {
        Reservation reservation = findReservation(serviceId, reservationId);

        if (request.getDate() != null) {
            reservation.setDate(request.getDate());
        }
        if (request.getDescription() != null) {
            reservation.setDescription(request.getDescription());
        }
        if (request.getStatut() != null) {
            reservation.setStatut(request.getStatut());
        }
        if (request.getUserId() != null) {
            reservation.setUserId(request.getUserId());
        }

        // Si le créneau change
        if (request.getCreneauId() != null &&
                (reservation.getCreneau() == null || !request.getCreneauId().equals(reservation.getCreneau().getId()))) {
            
            // Libérer l'ancien créneau
            if (reservation.getCreneau() != null) {
                reservation.getCreneau().setDisponible(true);
                creneauRepository.save(reservation.getCreneau());
            }

            Creneau nouveauCreneau = creneauRepository.findById(request.getCreneauId())
                    .orElseThrow(() -> new ResourceNotFoundException("Créneau introuvable avec l'ID: " + request.getCreneauId()));

            if (!nouveauCreneau.verifierDisponibilite()) {
                throw new BadRequestException("Le nouveau créneau n'est pas disponible.");
            }
            nouveauCreneau.setDisponible(false);
            creneauRepository.save(nouveauCreneau);
            reservation.setCreneau(nouveauCreneau);
        }

        Reservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    @Override
    public void deleteReservation(Long serviceId, Long reservationId) {
        Reservation reservation = findReservation(serviceId, reservationId);

        if (reservation.getCreneau() != null) {
            reservation.getCreneau().setDisponible(true);
            creneauRepository.save(reservation.getCreneau());
        }

        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationResponseDto confirmerReservation(Long serviceId, Long reservationId) {
        Reservation reservation = findReservation(serviceId, reservationId);
        reservation.confirmerReservation();
        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    @Override
    public ReservationResponseDto annulerReservation(Long serviceId, Long reservationId) {
        Reservation reservation = findReservation(serviceId, reservationId);
        reservation.annulerReservation();
        if (reservation.getCreneau() != null) {
            creneauRepository.save(reservation.getCreneau());
        }
        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    private Reservation findReservation(Long serviceId, Long reservationId) {
        if (reservationId == null) {
            throw new BadRequestException("L'identifiant de réservation est obligatoire.");
        }
        if (serviceId != null) {
            return reservationRepository.findByIdAndServiceId(reservationId, serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Réservation introuvable avec l'ID: " + reservationId + " pour le service: " + serviceId));
        }
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable avec l'ID: " + reservationId));
    }

    private ReservationResponseDto mapToResponse(Reservation r) {
        CreneauDto creneauDto = null;
        if (r.getCreneau() != null) {
            Creneau c = r.getCreneau();
            creneauDto = CreneauDto.builder()
                    .id(c.getId())
                    .date(c.getDate())
                    .heureDebut(c.getHeureDebut())
                    .heureFin(c.getHeureFin())
                    .disponible(c.getDisponible())
                    .serviceId(c.getServiceId())
                    .build();
        }

        return ReservationResponseDto.builder()
                .id(r.getId())
                .date(r.getDate())
                .statut(r.getStatut())
                .description(r.getDescription())
                .serviceId(r.getServiceId())
                .userId(r.getUserId())
                .creneau(creneauDto)
                .build();
    }
}
