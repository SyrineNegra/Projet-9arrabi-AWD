package tn.esprit.spring.produit.web.controller.dto;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.produit.domain.Produit;
import tn.esprit.spring.produit.service.ProduitServiceIMPL;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/produit")
public class ProduitController
{
    ProduitServiceIMPL produitService;
    @PostMapping("/ajouterProduit")
    @Operation(description="Cette méthode ajoute un produit dans la BD")
    public Produit ajouterProduit(@Valid @RequestBody Produit p)
    {
        return produitService.ajouterProduit(p);
    }

    @GetMapping("/listeProduit")
    @Operation(description="Cette méthode récupère la liste des produits.")
    public List<Produit> listerProduits()
    {
        return produitService.listerProduits();
    }

    @PutMapping("/modifierProduit/{id}")
    @Operation(description="Cette méthode modifie un produit dans la BD")
    public Produit modifierProduit(@RequestBody Produit p, @PathVariable Long id)
    {
        return produitService.modifierProduit(p, id);
    }
    @DeleteMapping("/supprimerProduit/{id}")
    @Operation(description="Cette méthode efface un produit de la BD")
    public void supprimerProduit(@PathVariable Long id)
    {
        produitService.supprimerProduit(id);
    }
}
