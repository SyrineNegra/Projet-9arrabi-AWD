package tn.esprit.spring.evenement.controller;

import tn.esprit.spring.evenement.entity.Evenement;
import tn.esprit.spring.evenement.service.IEvenementService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/evenements")
public class EvenementController {

    private final IEvenementService evenementService;

    public EvenementController(IEvenementService evenementService) {
        this.evenementService = evenementService;
    }

    // Ajouter un evenement
    @PostMapping
    public Evenement creerEvenement(@RequestBody Evenement evenement) {
        return evenementService.creerEvenement(evenement);
    }

    // Afficher tous les evenements
    @GetMapping
    public List<Evenement> afficherEvenements() {
        return evenementService.afficherEvenements();
    }

    // Afficher un evenement par ID
    @GetMapping("/{id}")
    public Evenement afficherEvenement(@PathVariable Long id) {
        return evenementService.afficherEvenement(id);
    }

    // Modifier un evenement
    @PutMapping("/{id}")
    public Evenement modifierEvenement(
            @PathVariable Long id,
            @RequestBody Evenement evenement) {

        return evenementService.modifierEvenement(id, evenement);
    }

    // Annuler (supprimer) un evenement
    @DeleteMapping("/{id}")
    public void annulerEvenement(@PathVariable Long id) {
        evenementService.annulerEvenement(id);
    }
}
