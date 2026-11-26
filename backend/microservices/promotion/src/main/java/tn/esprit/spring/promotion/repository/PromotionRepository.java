package tn.esprit.spring.promotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.promotion.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
