package com.example.sport_forever.ware.dto;

import jakarta.validation.constraints.NotBlank;

public record ReturnDto(
        @NotBlank(message = "물품 이름이 비어있습니다.")
        String wareName
)
{ }
