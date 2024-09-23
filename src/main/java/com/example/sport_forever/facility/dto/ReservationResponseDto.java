package com.example.sport_forever.facility.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        String facilityName,
        Boolean activate,
        LocalDateTime reservationTime,
        LocalDateTime endTime
) {
}
