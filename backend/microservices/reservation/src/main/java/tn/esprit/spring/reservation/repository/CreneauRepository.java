package tn.esprit.spring.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.reservation.entity.Creneau;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {

    List<Creneau> findByServiceId(Long serviceId);

    List<Creneau> findByServiceIdAndDisponibleTrue(Long serviceId);

    List<Creneau> findByServiceIdAndDate(Long serviceId, LocalDate date);
}
