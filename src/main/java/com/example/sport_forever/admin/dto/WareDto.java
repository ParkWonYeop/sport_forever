package com.example.sport_forever.admin.dto;

import com.example.sport_forever.common.enums.WareEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WareDto(
        @NotBlank(message = "이름이 입력해주세요.")
        String name,
        @NotNull(message = "카테고리를 입력해주세요.")
        WareEnum category,
        @NotNull(message = "최대 개수를 입력해주세요.")
        @Min(value = 1, message = "maxCount는 0보다 커야합니다.")
        long maxCount
) {
}
