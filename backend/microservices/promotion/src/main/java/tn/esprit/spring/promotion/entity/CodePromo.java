package tn.esprit.spring.promotion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodePromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private Integer quota;

    @Enumerated(EnumType.STRING)
    private StatutCodePromo status;

    private LocalDate dateExpiration;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    @JsonIgnore
    private Promotion promotion;
}
