package tn.esprit.spring.promotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.promotion.entity.CodePromo;
import tn.esprit.spring.promotion.entity.Promotion;
import tn.esprit.spring.promotion.entity.StatutCodePromo;
import tn.esprit.spring.promotion.entity.StatutPromotion;
import tn.esprit.spring.promotion.repository.CodePromoRepository;
import tn.esprit.spring.promotion.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CodePromoService {

    private final CodePromoRepository codePromoRepository;
    private final PromotionRepository promotionRepository;

    public List<CodePromo> getAllCodesPromo() {
        return codePromoRepository.findAll();
    }

    public Optional<CodePromo> getCodePromoById(Long id) {
        return codePromoRepository.findById(id);
    }

    public List<CodePromo> getCodesByPromotion(Long promotionId) {
        return codePromoRepository.findByPromotionId(promotionId);
    }

    public Optional<CodePromo> genererCode(Long promotionId, CodePromo codePromo) {
        Optional<Promotion> promotionOpt = promotionRepository.findById(promotionId);
        if (promotionOpt.isEmpty()) {
            return Optional.empty();
        }
        Promotion promotion = promotionOpt.get();

        if (codePromo.getCode() == null || codePromo.getCode().isBlank()) {
            String generated = "PROMO-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            codePromo.setCode(generated);
        }
        if (codePromo.getStatus() == null) {
            codePromo.setStatus(StatutCodePromo.ACTIF);
        }
        if (codePromo.getDateExpiration() == null) {
            codePromo.setDateExpiration(promotion.getDateFin());
        }
        codePromo.setPromotion(promotion);

        return Optional.of(codePromoRepository.save(codePromo));
    }

    public Optional<Boolean> validerCode(String code) {
        return codePromoRepository.findByCode(code).map(codePromo -> {
            if (codePromo.getStatus() != StatutCodePromo.ACTIF) {
                return false;
            }
            if (codePromo.getQuota() != null && codePromo.getQuota() <= 0) {
                return false;
            }
            LocalDate today = LocalDate.now();
            if (codePromo.getDateExpiration() != null && today.isAfter(codePromo.getDateExpiration())) {
                return false;
            }
            Promotion promotion = codePromo.getPromotion();
            if (promotion != null && promotion.getStatut() != StatutPromotion.ACTIVE) {
                return false;
            }
            return true;
        });
    }

    public Optional<CodePromo> revoquerCode(Long id) {
        return codePromoRepository.findById(id).map(codePromo -> {
            codePromo.setStatus(StatutCodePromo.REVOQUE);
            return codePromoRepository.save(codePromo);
        });
    }

    public Optional<Boolean> verifierQuota(Long id) {
        return codePromoRepository.findById(id)
                .map(codePromo -> codePromo.getQuota() != null && codePromo.getQuota() > 0);
    }

    public void supprimerCodePromo(Long id) {
        codePromoRepository.deleteById(id);
    }
}
