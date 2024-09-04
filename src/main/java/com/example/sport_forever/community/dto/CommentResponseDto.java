package com.example.sport_forever.community.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long commentId,
        String writerName,
        String content,
        LocalDateTime createdAt
) {}
