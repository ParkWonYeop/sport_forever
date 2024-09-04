package com.example.sport_forever.common.controller;

import com.example.sport_forever.common.jwt.JwtErrorException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ExceptionHandler({
            RuntimeException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<Object> handleBadRequestException(final RuntimeException runtimeException) {
        log.error(runtimeException.getMessage());
        return ResponseEntity.badRequest().body(runtimeException.getMessage());
    }

    @ExceptionHandler({
            AccessDeniedException.class
    })
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException accessDeniedException) {
        log.error(accessDeniedException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(accessDeniedException.getMessage());
    }

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<Object> handleException(final Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler({
            JwtErrorException.class
    })
    public ResponseEntity<Object> handleLibraryReservationException(final JwtErrorException jwtErrorException) {
        log.error(jwtErrorException.getMessage());
        return ResponseEntity.status(jwtErrorException.getHttpStatus()).body(jwtErrorException.getMessage());
    }

    @ExceptionHandler({
            SportException.class
    })
    public ResponseEntity<Object> handleSportException(final SportException sportException) {
        log.error(sportException.getMessage());
        return ResponseEntity.status(sportException.getHttpStatus()).body(sportException.getMessage());
    }
}
