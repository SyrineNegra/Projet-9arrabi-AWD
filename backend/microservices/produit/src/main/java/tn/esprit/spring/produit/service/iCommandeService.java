package tn.esprit.spring.produit.service;

import tn.esprit.spring.produit.domain.Commande;

import java.util.List;

public interface iCommandeService
{
    Commande ajouterCommande(Commande c);
    List<Commande> listerCommandes();
    Commande modifierCommande(Commande c, Long id);
    void supprimerCommande(Long id);

}
