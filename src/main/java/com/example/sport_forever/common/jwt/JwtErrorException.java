package com.example.sport_forever.common.jwt;

import com.example.sport_forever.common.enums.JwtErrorEnum;
import io.jsonwebtoken.JwtException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtErrorException extends JwtException {
    private final HttpStatus httpStatus;

    public JwtErrorException(JwtErrorEnum jwtErrorEnum) {
        super(jwtErrorEnum.getMessage());
        httpStatus = jwtErrorEnum.getHttpStatus();
    }
}
