package com.example.sport_forever.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorEnum {
    UNKNOWN_ERROR("UNKNOWN_ERROR", HttpStatus.UNAUTHORIZED),
    WRONG_TYPE_TOKEN("WRONG_TYPE_TOKEN", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("EXPIRED_TOKEN", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN("UNSUPPORTED_TOKEN", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("ACCESS_DENIED", HttpStatus.UNAUTHORIZED),
    NULL_PARAM("Null Param", HttpStatus.UNAUTHORIZED);

    final String message;
    final HttpStatus httpStatus;
}
