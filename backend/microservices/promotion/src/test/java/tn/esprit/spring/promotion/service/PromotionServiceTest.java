package tn.esprit.spring.promotion.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.promotion.entity.Promotion;
import tn.esprit.spring.promotion.entity.StatutPromotion;
import tn.esprit.spring.promotion.entity.TypeReduction;
import tn.esprit.spring.promotion.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromotionServiceTest {

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionService promotionService;

    @Test
    void creerPromotion_valide_retournePromotionActive() {
        Promotion promo = new Promotion();
        promo.setTitre("Soldes d'été");
        promo.setTypeReduction(TypeReduction.POURCENTAGE);
        promo.setDateDebut(LocalDate.now());
        promo.setDateFin(LocalDate.now().plusDays(10));

        when(promotionRepository.save(any(Promotion.class))).thenAnswer(inv -> inv.getArgument(0));

        Promotion saved = promotionService.creerPromotion(promo);
        assertEquals("Soldes d'été", saved.getTitre());
        assertEquals(StatutPromotion.ACTIVE, saved.getStatut());
    }

    @Test
    void creerPromotion_datesInvalides_lanceException() {
        Promotion promo = new Promotion();
        promo.setTitre("Promo invalide");
        promo.setDateDebut(LocalDate.now().plusDays(5));
        promo.setDateFin(LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> promotionService.creerPromotion(promo));
    }

    @Test
    void activerEtDesactiverPromotion() {
        Promotion promo = new Promotion();
        promo.setId(1L);
        promo.setStatut(StatutPromotion.INACTIVE);

        when(promotionRepository.findById(1L)).thenReturn(Optional.of(promo));
        when(promotionRepository.save(any(Promotion.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Promotion> activated = promotionService.activerPromotion(1L);
        assertTrue(activated.isPresent());
        assertEquals(StatutPromotion.ACTIVE, activated.get().getStatut());

        Optional<Promotion> deactivated = promotionService.desactiverPromotion(1L);
        assertTrue(deactivated.isPresent());
        assertEquals(StatutPromotion.INACTIVE, deactivated.get().getStatut());
    }

    @Test
    void verifierValidite_activeEtDansLesDates_retourneTrue() {
        Promotion promo = new Promotion();
        promo.setId(1L);
        promo.setStatut(StatutPromotion.ACTIVE);
        promo.setDateDebut(LocalDate.now().minusDays(1));
        promo.setDateFin(LocalDate.now().plusDays(5));

        when(promotionRepository.findById(1L)).thenReturn(Optional.of(promo));

        Optional<Boolean> valid = promotionService.verifierValidite(1L);
        assertTrue(valid.isPresent());
        assertTrue(valid.get());
    }
}
