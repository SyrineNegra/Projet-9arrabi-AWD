package tn.esprit.spring.reservation.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.spring.reservation.entity.Creneau;
import tn.esprit.spring.reservation.entity.Reservation;
import tn.esprit.spring.reservation.entity.StatutReservation;
import tn.esprit.spring.reservation.repository.CreneauRepository;
import tn.esprit.spring.reservation.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CreneauRepository creneauRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) {
        if (creneauRepository.count() == 0) {
            // Créneaux pour Service ID 1
            Creneau c1 = creneauRepository.save(Creneau.builder()
                    .date(LocalDate.now().plusDays(1))
                    .heureDebut(LocalTime.of(9, 0))
                    .heureFin(LocalTime.of(10, 0))
                    .disponible(false)
                    .serviceId(1L)
                    .build());

            Creneau c2 = creneauRepository.save(Creneau.builder()
                    .date(LocalDate.now().plusDays(1))
                    .heureDebut(LocalTime.of(10, 30))
                    .heureFin(LocalTime.of(11, 30))
                    .disponible(true)
                    .serviceId(1L)
                    .build());

            Creneau c3 = creneauRepository.save(Creneau.builder()
                    .date(LocalDate.now().plusDays(2))
                    .heureDebut(LocalTime.of(14, 0))
                    .heureFin(LocalTime.of(15, 0))
                    .disponible(true)
                    .serviceId(1L)
                    .build());

            // Créneaux pour Service ID 2
            Creneau c4 = creneauRepository.save(Creneau.builder()
                    .date(LocalDate.now().plusDays(1))
                    .heureDebut(LocalTime.of(16, 0))
                    .heureFin(LocalTime.of(17, 0))
                    .disponible(false)
                    .serviceId(2L)
                    .build());

            // Réservations d'exemple
            Reservation r1 = Reservation.builder()
                    .date(LocalDate.now().plusDays(1))
                    .statut(StatutReservation.CONFIRMEE.name())
                    .description("Réservation de consultation")
                    .serviceId(1L)
                    .userId(101L)
                    .creneau(c1)
                    .build();
            reservationRepository.save(r1);

            Reservation r2 = Reservation.builder()
                    .date(LocalDate.now().plusDays(1))
                    .statut(StatutReservation.EN_ATTENTE.name())
                    .description("Réservation séance de coaching")
                    .serviceId(2L)
                    .userId(102L)
                    .creneau(c4)
                    .build();
            reservationRepository.save(r2);
        }
    }
}
