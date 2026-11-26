package tn.esprit.spring.evenement.controller;

import tn.esprit.spring.evenement.entity.Participation;
import tn.esprit.spring.evenement.service.IParticipationService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    private final IParticipationService participationService;

    public ParticipationController(
            IParticipationService participationService) {
        this.participationService = participationService;
    }

    // Ajouter une participation a un evenement
    @PostMapping("/evenement/{evenementId}")
    public Participation ajouterParticipation(
            @PathVariable Long evenementId,
            @RequestBody Participation participation) {

        return participationService.ajouterParticipation(
                evenementId, participation);
    }

    // Afficher toutes les participations
    @GetMapping
    public List<Participation> afficherParticipations() {
        return participationService.afficherParticipations();
    }

    // Afficher une participation par ID
    @GetMapping("/{id}")
    public Participation afficherParticipation(@PathVariable Long id) {
        return participationService.afficherParticipation(id);
    }

    // Annuler une participation
    @PutMapping("/{id}/annuler")
    public Participation annulerParticipation(@PathVariable Long id) {
        return participationService.annulerParticipation(id);
    }
}
