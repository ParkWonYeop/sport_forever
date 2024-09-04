package com.example.sport_forever.common.controller;

import com.example.sport_forever.common.controller.constant.CommunalResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SportException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public SportException(CommunalResponse communalResponse) {
        this.httpStatus = communalResponse.getHttpStatus();
        this.message = communalResponse.getMessage();
    }
}
