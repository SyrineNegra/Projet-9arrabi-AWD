package tn.esprit.spring.promotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.promotion.entity.CodePromo;
import tn.esprit.spring.promotion.entity.Promotion;
import tn.esprit.spring.promotion.entity.StatutPromotion;
import tn.esprit.spring.promotion.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Optional<Promotion> getPromotionById(Long id) {
        return promotionRepository.findById(id);
    }

    public Promotion creerPromotion(Promotion promotion) {
        if (promotion.getTitre() == null || promotion.getTitre().isBlank()) {
            throw new IllegalArgumentException("Le titre de la promotion est obligatoire.");
        }
        if (promotion.getDateDebut() != null && promotion.getDateFin() != null
                && promotion.getDateFin().isBefore(promotion.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début.");
        }
        if (promotion.getStatut() == null) {
            promotion.setStatut(StatutPromotion.ACTIVE);
        }
        if (promotion.getCodesPromo() != null) {
            for (CodePromo codePromo : promotion.getCodesPromo()) {
                codePromo.setPromotion(promotion);
            }
        }
        return promotionRepository.save(promotion);
    }

    public Optional<Promotion> modifierPromotion(Long id, Promotion promotionDetails) {
        return promotionRepository.findById(id).map(existing -> {
            existing.setTitre(promotionDetails.getTitre());
            existing.setDescription(promotionDetails.getDescription());
            existing.setTypeReduction(promotionDetails.getTypeReduction());
            existing.setDateDebut(promotionDetails.getDateDebut());
            existing.setDateFin(promotionDetails.getDateFin());
            if (promotionDetails.getStatut() != null) {
                existing.setStatut(promotionDetails.getStatut());
            }
            return promotionRepository.save(existing);
        });
    }

    public void supprimerPromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    public Optional<Promotion> activerPromotion(Long id) {
        return promotionRepository.findById(id).map(promotion -> {
            promotion.setStatut(StatutPromotion.ACTIVE);
            return promotionRepository.save(promotion);
        });
    }

    public Optional<Promotion> desactiverPromotion(Long id) {
        return promotionRepository.findById(id).map(promotion -> {
            promotion.setStatut(StatutPromotion.INACTIVE);
            return promotionRepository.save(promotion);
        });
    }

    public Optional<Boolean> verifierValidite(Long id) {
        return promotionRepository.findById(id).map(promotion -> {
            if (promotion.getStatut() != StatutPromotion.ACTIVE) {
                return false;
            }
            LocalDate today = LocalDate.now();
            boolean apresDebut = promotion.getDateDebut() == null || !today.isBefore(promotion.getDateDebut());
            boolean avantFin = promotion.getDateFin() == null || !today.isAfter(promotion.getDateFin());
            return apresDebut && avantFin;
        });
    }
}
