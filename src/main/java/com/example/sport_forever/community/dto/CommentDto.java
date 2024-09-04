package com.example.sport_forever.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentDto(
        @NotNull
        Long postId,
        @NotBlank
        String content
) {
}
