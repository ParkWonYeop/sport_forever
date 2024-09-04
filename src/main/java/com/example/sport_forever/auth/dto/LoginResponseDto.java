package com.example.sport_forever.auth.dto;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
) {
}
