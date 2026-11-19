package tn.esprit.spring.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    private Long id;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    private String statut;
    private String description;

    private Long serviceId;
    private Long userId;

    private Long creneauId;
    private CreneauDto creneau;
}
