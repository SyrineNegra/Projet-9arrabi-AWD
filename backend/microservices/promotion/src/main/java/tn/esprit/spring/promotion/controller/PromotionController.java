package tn.esprit.spring.promotion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.promotion.entity.Promotion;
import tn.esprit.spring.promotion.service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        return promotionService.getPromotionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Promotion> creerPromotion(@RequestBody Promotion promotion) {
        try {
            Promotion created = promotionService.creerPromotion(promotion);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> modifierPromotion(@PathVariable Long id, @RequestBody Promotion promotion) {
        return promotionService.modifierPromotion(id, promotion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPromotion(@PathVariable Long id) {
        promotionService.supprimerPromotion(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activer")
    public ResponseEntity<Promotion> activerPromotion(@PathVariable Long id) {
        return promotionService.activerPromotion(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Promotion> desactiverPromotion(@PathVariable Long id) {
        return promotionService.desactiverPromotion(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/validite")
    public ResponseEntity<Boolean> verifierValidite(@PathVariable Long id) {
        return promotionService.verifierValidite(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
