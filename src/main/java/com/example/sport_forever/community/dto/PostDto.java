package com.example.sport_forever.community.dto;

import jakarta.validation.constraints.NotBlank;

public record PostDto(
        @NotBlank
        String title,
        @NotBlank
        String subject
) { }
