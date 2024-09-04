package com.example.sport_forever.ware.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record RentalDto(
        @NotBlank(message = "대여할 물품을 입력하세요.")
        String wareName,
        @Future(message = "예약시간이 현재보다 과거입니다.")
        LocalDateTime rentalTime,
        @Future(message = "종료시간이 현재보다 과거입니다.")
        LocalDateTime endTime
) {
}
