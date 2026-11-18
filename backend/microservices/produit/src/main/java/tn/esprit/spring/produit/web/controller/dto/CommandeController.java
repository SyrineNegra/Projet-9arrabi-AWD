package tn.esprit.spring.produit.web.controller.dto;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.produit.domain.Commande;
import tn.esprit.spring.produit.service.CommandeServiceIMPL;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/commande")
public class CommandeController
{
    CommandeServiceIMPL CommandeService;
    @PostMapping("/ajouterCommande")
    @Operation(description="Cette méthode ajoute une commande dans la BD")
    public Commande ajouterCommande(@Valid @RequestBody Commande s)
    {
        System.out.println("=== DEBUG COMMANDE ===");
        System.out.println("date           = " + s.getDate());
        System.out.println("status         = " + s.getStatus());
        System.out.println("total          = " + s.getTotal());
        System.out.println("fraisLivraison = " + s.getFraisLivraison());
        System.out.println("produit        = " + s.getProduit());
        return CommandeService.ajouterCommande(s);
    }

    @GetMapping("/listeCommande")
    @Operation(description="Cette méthode récupère la liste des Commandes.")
    public List<Commande> listerCommandes()
    {
        return CommandeService.listerCommandes();
    }

    @PutMapping("/modifierCommande/{id}")
    @Operation(description="Cette méthode modifie une Commande dans la BD")
    public Commande modifierCommande(@RequestBody Commande s, @PathVariable Long id)
    {
        return CommandeService.modifierCommande(s, id);
    }
    @DeleteMapping("/supprimerCommande/{id}")
    @Operation(description="Cette méthode efface une Commande de la BD")
    public void supprimerCommande(@PathVariable Long id)
    {
        CommandeService.supprimerCommande(id);
    }
}
