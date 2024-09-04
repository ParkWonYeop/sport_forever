package com.example.sport_forever.community.dto;

import java.time.LocalDateTime;

public record PostListResponseDto(
        Long postId,
        String writer,
        String title,
        LocalDateTime createdAt
) {
}
