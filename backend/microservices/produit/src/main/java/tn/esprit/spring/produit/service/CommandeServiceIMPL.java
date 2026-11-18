package tn.esprit.spring.produit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.produit.domain.Commande;
import tn.esprit.spring.produit.domain.Produit;
import tn.esprit.spring.produit.repository.CommandeRepository;
import tn.esprit.spring.produit.repository.ProduitRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandeServiceIMPL implements iCommandeService
{
    CommandeRepository CommandeRepository;
    @Override
    public Commande ajouterCommande(Commande s) {
        return CommandeRepository.save(s);
    }

    @Override
    public List<Commande> listerCommandes() {
        return (List<Commande>)CommandeRepository.findAll();
    }

    @Override
    public Commande modifierCommande(Commande p, Long id) {
        Commande Commandeamodifier=CommandeRepository.findById(id).orElse(null);
        return CommandeRepository.save(Commandeamodifier);
    }

    @Override
    public void supprimerCommande(Long id)
    {
        CommandeRepository.deleteById(id);

    }
}
