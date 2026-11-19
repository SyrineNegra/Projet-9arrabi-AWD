package tn.esprit.spring.reservation.service;

import tn.esprit.spring.reservation.dto.CreneauDto;
import tn.esprit.spring.reservation.entity.Creneau;

import java.util.List;

public interface ICreneauService {

    Creneau createCreneau(CreneauDto creneauDto);

    List<Creneau> getCreneauxByService(Long serviceId);

    List<Creneau> getAvailableCreneauxByService(Long serviceId);

    Creneau getCreneauById(Long id);

    boolean verifierDisponibilite(Long id);

    void deleteCreneau(Long id);
}
