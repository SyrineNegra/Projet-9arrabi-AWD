package tn.esprit.spring.produit.service;

import tn.esprit.spring.produit.domain.Produit;

import java.util.List;

public interface iProduitService
{
    Produit ajouterProduit(Produit p);
    List<Produit> listerProduits();
    Produit modifierProduit(Produit p, Long id);
    void  supprimerProduit(Long id);

}
