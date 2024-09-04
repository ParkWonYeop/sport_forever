package com.example.sport_forever.admin.dto;

import com.example.sport_forever.common.enums.WareEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FacilityDto(
        @NotBlank(message = "이름이 입력해주세요.")
        String name
) {
}
