package tn.esprit.spring.promotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.promotion.entity.CodePromo;

import java.util.List;
import java.util.Optional;

public interface CodePromoRepository extends JpaRepository<CodePromo, Long> {
    Optional<CodePromo> findByCode(String code);
    List<CodePromo> findByPromotionId(Long promotionId);
}
