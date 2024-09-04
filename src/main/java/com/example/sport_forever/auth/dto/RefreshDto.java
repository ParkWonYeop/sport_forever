package com.example.sport_forever.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RefreshDto(
        @NotBlank(message = "빈 문자열 입니다.")
        @Pattern(regexp = "[0-9]{8,12}", message = "전화번호 형식을 맞춰주세요.")
        String phoneNumber,
        @NotBlank(message = "빈 문자열 입니다.")
        String refreshToken
) {}
