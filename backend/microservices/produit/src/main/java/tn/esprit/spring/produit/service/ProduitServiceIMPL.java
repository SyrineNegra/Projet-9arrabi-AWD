package tn.esprit.spring.produit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.produit.domain.Produit;
import tn.esprit.spring.produit.repository.ProduitRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProduitServiceIMPL implements iProduitService {

    ProduitRepository produitRepository;
    @Override
    public Produit ajouterProduit(Produit p) {
        return produitRepository.save(p);
    }

    @Override
    public List<Produit> listerProduits() {
        return (List<Produit>)produitRepository.findAll();
    }

    @Override
    public Produit modifierProduit(Produit p, Long id) {
        Produit produitamodifier=produitRepository.findById(id).orElse(null);
        return produitRepository.save(produitamodifier);
    }

    @Override
    public void supprimerProduit(Long id)
    {
    produitRepository.deleteById(id);

    }
}
