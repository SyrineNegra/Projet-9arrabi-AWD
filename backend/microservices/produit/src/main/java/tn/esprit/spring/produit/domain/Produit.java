package tn.esprit.spring.produit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Table(name = "produit")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Produit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=30)
    @NotBlank private String nom;
    @Column(nullable=true, length=200)
    private String description;
    @Column(nullable=false)
    @NotBlank private double prix;
    @Column(nullable=false)
    @NotBlank private int stock;
    @Column(nullable=false, length=30)
    @NotBlank
    private String categorie;
    /*
    @OneToMany(mappedBy="produit")
    @JsonIgnore
    List<Commande> commande;
     */
}
