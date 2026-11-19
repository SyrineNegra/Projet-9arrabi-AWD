package tn.esprit.spring.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.reservation.dto.CreneauDto;
import tn.esprit.spring.reservation.entity.Creneau;
import tn.esprit.spring.reservation.service.ICreneauService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CreneauController {

    private final ICreneauService creneauService;

    /**
     * Obtenir tous les créneaux d'un service
     */
    @GetMapping("/{id}/creneaux")
    public ResponseEntity<List<Creneau>> getCreneauxByService(@PathVariable("id") Long id) {
        List<Creneau> creneaux = creneauService.getCreneauxByService(id);
        return ResponseEntity.ok(creneaux);
    }

    /**
     * Obtenir les créneaux disponibles d'un service
     */
    @GetMapping("/{id}/creneaux/disponibles")
    public ResponseEntity<List<Creneau>> getAvailableCreneaux(@PathVariable("id") Long id) {
        List<Creneau> creneaux = creneauService.getAvailableCreneauxByService(id);
        return ResponseEntity.ok(creneaux);
    }

    /**
     * Créer un créneau pour un service
     */
    @PostMapping("/{id}/creneaux")
    public ResponseEntity<Creneau> createCreneau(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreneauDto dto) {
        dto.setServiceId(id);
        Creneau created = creneauService.createCreneau(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Vérifier la disponibilité d'un créneau
     */
    @GetMapping("/creneaux/{creneauId}/disponibilite")
    public ResponseEntity<Map<String, Object>> verifierDisponibilite(@PathVariable("creneauId") Long creneauId) {
        boolean disponible = creneauService.verifierDisponibilite(creneauId);
        return ResponseEntity.ok(Map.of("creneauId", creneauId, "disponible", disponible));
    }

    /**
     * Supprimer un créneau
     */
    @DeleteMapping("/creneaux/{creneauId}")
    public ResponseEntity<Void> deleteCreneau(@PathVariable("creneauId") Long creneauId) {
        creneauService.deleteCreneau(creneauId);
        return ResponseEntity.noContent().build();
    }
}
