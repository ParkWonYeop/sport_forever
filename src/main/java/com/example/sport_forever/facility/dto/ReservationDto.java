package com.example.sport_forever.facility.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ReservationDto(
        @NotBlank(message = "대여할 물품을 입력하세요.")
        String facilityName,
        @Future(message = "예약시간이 현재보다 과거입니다.")
        LocalDateTime reservationTime,
        @Future(message = "종료시간이 현재보다 과거입니다.")
        LocalDateTime endTime
) {
}
