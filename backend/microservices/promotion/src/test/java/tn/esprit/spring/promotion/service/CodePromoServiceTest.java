package tn.esprit.spring.promotion.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.promotion.entity.CodePromo;
import tn.esprit.spring.promotion.entity.Promotion;
import tn.esprit.spring.promotion.entity.StatutCodePromo;
import tn.esprit.spring.promotion.entity.StatutPromotion;
import tn.esprit.spring.promotion.repository.CodePromoRepository;
import tn.esprit.spring.promotion.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodePromoServiceTest {

    @Mock
    private CodePromoRepository codePromoRepository;

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private CodePromoService codePromoService;

    @Test
    void genererCode_genereCodeAutomatiqueEtLiePromotion() {
        Promotion promo = new Promotion();
        promo.setId(1L);
        promo.setDateFin(LocalDate.now().plusDays(15));

        CodePromo input = new CodePromo();
        input.setQuota(50);

        when(promotionRepository.findById(1L)).thenReturn(Optional.of(promo));
        when(codePromoRepository.save(any(CodePromo.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<CodePromo> result = codePromoService.genererCode(1L, input);
        assertTrue(result.isPresent());
        assertNotNull(result.get().getCode());
        assertTrue(result.get().getCode().startsWith("PROMO-"));
        assertEquals(StatutCodePromo.ACTIF, result.get().getStatus());
        assertEquals(promo, result.get().getPromotion());
    }

    @Test
    void validerCode_actifAvecQuotaEtNonExpire_retourneTrue() {
        Promotion promo = new Promotion();
        promo.setStatut(StatutPromotion.ACTIVE);

        CodePromo code = new CodePromo();
        code.setCode("PROMO-123456");
        code.setQuota(10);
        code.setStatus(StatutCodePromo.ACTIF);
        code.setDateExpiration(LocalDate.now().plusDays(5));
        code.setPromotion(promo);

        when(codePromoRepository.findByCode("PROMO-123456")).thenReturn(Optional.of(code));

        Optional<Boolean> valid = codePromoService.validerCode("PROMO-123456");
        assertTrue(valid.isPresent());
        assertTrue(valid.get());
    }

    @Test
    void revoquerCode_passeStatutRevoque() {
        CodePromo code = new CodePromo();
        code.setId(1L);
        code.setStatus(StatutCodePromo.ACTIF);

        when(codePromoRepository.findById(1L)).thenReturn(Optional.of(code));
        when(codePromoRepository.save(any(CodePromo.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<CodePromo> revoked = codePromoService.revoquerCode(1L);
        assertTrue(revoked.isPresent());
        assertEquals(StatutCodePromo.REVOQUE, revoked.get().getStatus());
    }

    @Test
    void verifierQuota_retourneTrueSiSuperieurAZero() {
        CodePromo code = new CodePromo();
        code.setId(1L);
        code.setQuota(5);

        when(codePromoRepository.findById(1L)).thenReturn(Optional.of(code));

        Optional<Boolean> hasQuota = codePromoService.verifierQuota(1L);
        assertTrue(hasQuota.isPresent());
        assertTrue(hasQuota.get());
    }
}
