package tn.esprit.spring.promotion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.promotion.entity.CodePromo;
import tn.esprit.spring.promotion.service.CodePromoService;

import java.util.List;

@RestController
@RequestMapping("/api/codes-promo")
@RequiredArgsConstructor
public class CodePromoController {

    private final CodePromoService codePromoService;

    @GetMapping
    public ResponseEntity<List<CodePromo>> getAllCodesPromo() {
        return ResponseEntity.ok(codePromoService.getAllCodesPromo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodePromo> getCodePromoById(@PathVariable Long id) {
        return codePromoService.getCodePromoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/promotion/{promotionId}")
    public ResponseEntity<List<CodePromo>> getCodesByPromotion(@PathVariable Long promotionId) {
        return ResponseEntity.ok(codePromoService.getCodesByPromotion(promotionId));
    }

    @PostMapping("/generer/{promotionId}")
    public ResponseEntity<CodePromo> genererCode(@PathVariable Long promotionId,
                                                 @RequestBody(required = false) CodePromo codePromo) {
        CodePromo body = codePromo != null ? codePromo : new CodePromo();
        return codePromoService.genererCode(promotionId, body)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/valider/{code}")
    public ResponseEntity<Boolean> validerCode(@PathVariable String code) {
        return codePromoService.validerCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/revoquer")
    public ResponseEntity<CodePromo> revoquerCode(@PathVariable Long id) {
        return codePromoService.revoquerCode(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/quota")
    public ResponseEntity<Boolean> verifierQuota(@PathVariable Long id) {
        return codePromoService.verifierQuota(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCodePromo(@PathVariable Long id) {
        codePromoService.supprimerCodePromo(id);
        return ResponseEntity.noContent().build();
    }
}
