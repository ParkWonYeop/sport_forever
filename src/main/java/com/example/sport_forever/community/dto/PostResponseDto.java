package com.example.sport_forever.community.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long postId,
        String writerName,
        String title,
        String subject,
        List<CommentResponseDto> commentList,
        LocalDateTime createdAt
) { }
