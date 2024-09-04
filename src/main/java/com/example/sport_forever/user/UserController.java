package com.example.sport_forever.user;

import com.example.sport_forever.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="User", description = "유저 관련 기능")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @Operation(
            operationId = "유저정보 조회",
            summary = "현재 로그인 되어있는 유저 정보를 조회합니다.",
            description = "현재 로그인 되어있는 유저의 휴대폰 번호, 이름, 권한, 밴 내역을 조회합니다."
    )
    @GetMapping
    public UserResponseDto getUserInfo() {
        return userService.getUserInfo();
    }

    @Operation(
            operationId = "비밀번호 변경",
            summary = "현재 로그인 되어있는 유저 비밀번호를 변경합니다.",
            description = "현재 로그인 되어있는 유저 비밀번호를 변경합니다."
    )
    @PutMapping("/password")
    public void changePassword() {

    }
}
