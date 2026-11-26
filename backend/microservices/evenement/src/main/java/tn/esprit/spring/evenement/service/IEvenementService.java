package tn.esprit.spring.evenement.service;

import tn.esprit.spring.evenement.entity.Evenement;
import java.util.List;

public interface IEvenementService {

    Evenement creerEvenement(Evenement evenement);

    Evenement modifierEvenement(Long id, Evenement evenement);

    List<Evenement> afficherEvenements();

    Evenement afficherEvenement(Long id);

    void annulerEvenement(Long id);
}
