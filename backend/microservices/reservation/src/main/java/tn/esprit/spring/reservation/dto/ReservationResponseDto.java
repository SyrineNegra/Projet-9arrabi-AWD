package tn.esprit.spring.reservation.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {

    private Long id;
    private LocalDate date;
    private String statut;
    private String description;
    private Long serviceId;
    private Long userId;
    private CreneauDto creneau;
}
