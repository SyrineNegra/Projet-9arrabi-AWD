package tn.esprit.spring.evenement.service;

import tn.esprit.spring.evenement.entity.Evenement;
import tn.esprit.spring.evenement.entity.Participation;
import tn.esprit.spring.evenement.entity.StatutParticipation;

import tn.esprit.spring.evenement.repository.EvenementRepository;
import tn.esprit.spring.evenement.repository.ParticipationRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class ParticipationServiceImpl
        implements IParticipationService {

    private final ParticipationRepository participationRepository;
    private final EvenementRepository evenementRepository;

    public ParticipationServiceImpl(
            ParticipationRepository participationRepository,
            EvenementRepository evenementRepository) {

        this.participationRepository = participationRepository;
        this.evenementRepository = evenementRepository;
    }

    @Override
    public Participation ajouterParticipation(
            Long evenementId,
            Participation participation) {

        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evenement introuvable"
                        ));

        long nombreParticipations =
                participationRepository.countByEvenement_Id(evenementId);

        if (nombreParticipations >= evenement.getCapacite()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Capacite maximale atteinte"
            );
        }

        participation.setEvenement(evenement);
        participation.setDateInscription(new Date());
        participation.setStatut(StatutParticipation.EN_ATTENTE);

        return participationRepository.save(participation);
    }

    @Override
    public List<Participation> afficherParticipations() {
        return participationRepository.findAll();
    }

    @Override
    public Participation afficherParticipation(Long id) {
        return participationRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Participation introuvable"
                        ));
    }

    @Override
    public Participation annulerParticipation(Long id) {

        Participation participation = afficherParticipation(id);

        participation.setStatut(StatutParticipation.ANNULEE);

        return participationRepository.save(participation);
    }
}
