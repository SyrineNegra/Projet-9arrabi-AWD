package tn.esprit.spring.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.reservation.entity.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByServiceId(Long serviceId);

    Optional<Reservation> findByIdAndServiceId(Long id, Long serviceId);

    List<Reservation> findByServiceIdAndStatut(Long serviceId, String statut);

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByServiceIdAndDate(Long serviceId, LocalDate date);

    void deleteByIdAndServiceId(Long id, Long serviceId);
}
