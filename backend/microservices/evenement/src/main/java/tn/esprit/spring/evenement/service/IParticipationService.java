package tn.esprit.spring.evenement.service;

import tn.esprit.spring.evenement.entity.Participation;
import java.util.List;

public interface IParticipationService {

    Participation ajouterParticipation(
            Long evenementId,
            Participation participation
    );

    List<Participation> afficherParticipations();

    Participation afficherParticipation(Long id);

    Participation annulerParticipation(Long id);
}
