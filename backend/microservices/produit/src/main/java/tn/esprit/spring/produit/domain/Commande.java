package tn.esprit.spring.produit.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "commande")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Commande
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date Date;
    @Column(nullable = false, length = 20)
    private String Status;
    @Column(nullable = false)
    private float total;
    @Column(nullable = false)
    private float fraisLivraison;

    @ManyToOne
    Produit produit;
}
