package tn.esprit.spring.produit.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "produit")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false, unique=true, length=30)
    private String nom;
    @Column(nullable=true, length=200)
    private String description;
    @Column(nullable=false)
    private double prix;
    @Column(nullable=false)
    private int stock;
    @Column(nullable=false)
    private String categorie;

}
