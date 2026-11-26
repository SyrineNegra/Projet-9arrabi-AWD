package tn.esprit.spring.evenement.service;

import tn.esprit.spring.evenement.entity.Evenement;
import tn.esprit.spring.evenement.repository.EvenementRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EvenementServiceImpl implements IEvenementService {

    private final EvenementRepository evenementRepository;

    public EvenementServiceImpl(EvenementRepository evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    @Override
    public Evenement creerEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    @Override
    public Evenement modifierEvenement(Long id, Evenement evenement) {

        Evenement ancien = afficherEvenement(id);

        ancien.setTitre(evenement.getTitre());
        ancien.setDescription(evenement.getDescription());
        ancien.setCapacite(evenement.getCapacite());
        ancien.setTypeEvenement(evenement.getTypeEvenement());
        ancien.setDateDebut(evenement.getDateDebut());
        ancien.setDateFin(evenement.getDateFin());

        return evenementRepository.save(ancien);
    }

    @Override
    public List<Evenement> afficherEvenements() {
        return evenementRepository.findAll();
    }

    @Override
    public Evenement afficherEvenement(Long id) {
        return evenementRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evenement introuvable"
                        ));
    }

    @Override
    public void annulerEvenement(Long id) {
        Evenement evenement = afficherEvenement(id);
        evenementRepository.delete(evenement);
    }
}
