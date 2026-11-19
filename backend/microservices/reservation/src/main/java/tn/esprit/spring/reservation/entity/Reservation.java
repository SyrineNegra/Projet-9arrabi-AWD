package tn.esprit.spring.reservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String statut; // EN_ATTENTE, CONFIRMEE, ANNULEE

    private String description;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "creneau_id")
    private Creneau creneau;

    public void creerReservation() {
        this.statut = StatutReservation.EN_ATTENTE.name();
        if (this.creneau != null) {
            this.creneau.setDisponible(false);
        }
    }

    public void annulerReservation() {
        this.statut = StatutReservation.ANNULEE.name();
        if (this.creneau != null) {
            this.creneau.setDisponible(true);
        }
    }

    public void confirmerReservation() {
        this.statut = StatutReservation.CONFIRMEE.name();
    }
}
