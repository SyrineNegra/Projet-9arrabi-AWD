package tn.esprit.spring.evenement.repository;

import tn.esprit.spring.evenement.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenementRepository
        extends JpaRepository<Evenement, Long> {
}
