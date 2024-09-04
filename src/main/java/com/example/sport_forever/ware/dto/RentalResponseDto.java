package com.example.sport_forever.ware.dto;

import java.time.LocalDateTime;

public record RentalResponseDto(
        String wareName,
        LocalDateTime rentalTime,
        LocalDateTime endTime
) {
}
