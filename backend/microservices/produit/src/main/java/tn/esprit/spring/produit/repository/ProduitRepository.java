package tn.esprit.spring.produit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.produit.domain.Produit;

@Repository
public interface ProduitRepository extends CrudRepository<Produit, Long> {
}
