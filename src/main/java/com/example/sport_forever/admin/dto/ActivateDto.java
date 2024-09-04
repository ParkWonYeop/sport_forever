package com.example.sport_forever.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ActivateDto(
        @Pattern(regexp = "[0-9]{8,12}", message = "전화번호 형식을 맞춰주세요.")
        @NotBlank(message = "빈 문자열 입니다.")
        String phoneNumber,
        @NotBlank(message = "이름이 입력해주세요.")
        String wareName
) {
}
