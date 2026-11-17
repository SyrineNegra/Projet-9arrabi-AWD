package tn.esprit.spring.produit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.produit.domain.Commande;

@Repository
public interface CommandeRepository extends CrudRepository<Commande, Long> {
}
