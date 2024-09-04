package com.example.sport_forever.facility.dto;

import jakarta.validation.constraints.NotBlank;

public record EndDto(
        @NotBlank(message = "물품 이름이 비어있습니다.")
        String facilityName
) {
}
