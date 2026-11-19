package tn.esprit.spring.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.reservation.dto.CreneauDto;
import tn.esprit.spring.reservation.entity.Creneau;
import tn.esprit.spring.reservation.exception.ResourceNotFoundException;
import tn.esprit.spring.reservation.repository.CreneauRepository;
import tn.esprit.spring.reservation.service.ICreneauService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CreneauServiceImpl implements ICreneauService {

    private final CreneauRepository creneauRepository;

    @Override
    public Creneau createCreneau(CreneauDto dto) {
        Creneau creneau = Creneau.builder()
                .date(dto.getDate())
                .heureDebut(dto.getHeureDebut())
                .heureFin(dto.getHeureFin())
                .disponible(dto.getDisponible() != null ? dto.getDisponible() : true)
                .serviceId(dto.getServiceId())
                .build();
        return creneauRepository.save(creneau);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Creneau> getCreneauxByService(Long serviceId) {
        return creneauRepository.findByServiceId(serviceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Creneau> getAvailableCreneauxByService(Long serviceId) {
        return creneauRepository.findByServiceIdAndDisponibleTrue(serviceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Creneau getCreneauById(Long id) {
        return creneauRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Créneau introuvable avec l'id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifierDisponibilite(Long id) {
        Creneau creneau = getCreneauById(id);
        return creneau.verifierDisponibilite();
    }

    @Override
    public void deleteCreneau(Long id) {
        if (!creneauRepository.existsById(id)) {
            throw new ResourceNotFoundException("Créneau introuvable avec l'id: " + id);
        }
        creneauRepository.deleteById(id);
    }
}
