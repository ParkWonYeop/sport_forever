package com.example.sport_forever.user.dto;

import com.example.sport_forever.common.enums.PermissionEnum;

import java.time.LocalDateTime;

public record UserResponseDto(
        String phoneNumber,
        String name,
        PermissionEnum permissionEnum,
        long banCount,
        LocalDateTime banTime
) {
}
