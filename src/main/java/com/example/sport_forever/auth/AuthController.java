package com.example.sport_forever.auth;

import com.example.sport_forever.auth.dto.*;
import com.example.sport_forever.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name="Auth", description = "인증 관련 기능")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @Operation(
            operationId = "로그인",
            summary = "로그인하여 JWT를 반환합니다.",
            description = "사용자 전화번호, 비밀번호를 요청받아 로그인합니다."
    )
    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @Operation(
            operationId = "회원가입",
            summary = "회원가입하여 유저를 추가합니다.",
            description = "사용자 전화번호, 비밀번호, 이름을 요청받아 회원가입합니다."
    )
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@Valid @RequestBody SignupDto signupDto) {
        authService.signup(signupDto);
    }

    @Operation(
            operationId = "토큰 새로고침",
            summary = "리프레쉬 토큰을 받아 새로운 엑세스 토큰을 반환합니다.",
            description = "한번 사용한 리프레쉬 토큰은 비활성화 됩니다. 새로운 리프레쉬 토큰을 사용해야합니다."
    )
    @PutMapping("/token")
    public LoginResponseDto refreshToken(@Valid @RequestBody RefreshDto refreshDto) {
        return authService.refreshToken(refreshDto);
    }
}
