package com.example.sport_forever.community.dto;

import jakarta.validation.constraints.NotNull;

public record CommentDeleteDto(
        @NotNull
        Long commentId
) {
}
