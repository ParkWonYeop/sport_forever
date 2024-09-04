package com.example.sport_forever.common.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommunalResponse {
    //Auth
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "유저를 찾을 수 없습니다."),
    USER_NOT_CORRECT(HttpStatus.BAD_REQUEST, "유저가 일치 하지 않습니다."),
    ALREADY_SIGNUP_PHONENUMBER(HttpStatus.BAD_REQUEST, "이미 가입한 전화번호입니다."),
    //Ware
    WARE_NOT_FOUND(HttpStatus.BAD_REQUEST, "물품을 찾을 수 없습니다."),
    WARE_COUNT_ZERO(HttpStatus.BAD_REQUEST, "남은 물품 개수가 없습니다."),
    ALREADY_RENTAL(HttpStatus.BAD_REQUEST, "이미 예약한 유저입니다."),
    RENTAL_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 유저의 예약을 찾을 수 없습니다."),
    BANED_USER(HttpStatus.BAD_REQUEST,"이용이 정지 된 유저입니다."),
    //Admin
    WARE_ALREADY_ADD(HttpStatus.BAD_REQUEST,"이미 존재하는 물품입니다."),
    WARE_COUNT_MINUS(HttpStatus.BAD_REQUEST, "물품의 최대 개수가 마이너스 입니다."),
    FILE_IS_EMPTY(HttpStatus.BAD_REQUEST,"파일이 비어있습니다."),
    BAN_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 유저는 밴 되어있지 않습니다."),
    FACILITY_ALREADY_ADD(HttpStatus.BAD_REQUEST, "이미 존재하는 시설입니다."),
    //Facility
    FACILITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "시설을 찾을 수 없습니다."),
    ALREADY_RESERVATION_USER(HttpStatus.BAD_REQUEST, "이미 시설을 예약한 유저입니다."),
    ALREADY_RESERVATION_FACILITY(HttpStatus.BAD_REQUEST, "이미 예약된 시설입니다."),
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"예약되지 않은 시설입니다."),
    RESERVATION_USER_NOT_CORRECT(HttpStatus.BAD_REQUEST,"해당 유저가 예약한 시설이 아닙니다."),
    RESERVATION_NOT_ABLE(HttpStatus.BAD_REQUEST,"예약이 불가능한 상태의 시설입니다."),
    //Community
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "포스트를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다.");

    final HttpStatus httpStatus;
    final String message;
}
