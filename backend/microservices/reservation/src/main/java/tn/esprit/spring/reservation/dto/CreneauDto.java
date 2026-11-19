package tn.esprit.spring.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreneauDto {
    private Long id;

    @NotNull(message = "La date du creneau est obligatoire")
    private LocalDate date;

    @NotNull(message = "L'heure de debut est obligatoire")
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime heureFin;

    private Boolean disponible;
    private Long serviceId;
}
