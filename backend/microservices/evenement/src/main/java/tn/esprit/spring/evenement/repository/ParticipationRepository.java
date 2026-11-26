package tn.esprit.spring.evenement.repository;

import tn.esprit.spring.evenement.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository
        extends JpaRepository<Participation, Long> {

    long countByEvenement_Id(Long evenementId);
}
