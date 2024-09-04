package com.example.sport_forever.auth;

import com.example.sport_forever.auth.dto.*;
import com.example.sport_forever.common.controller.SportException;
import com.example.sport_forever.common.controller.constant.CommunalResponse;
import com.example.sport_forever.common.jwt.JwtUtil;
import com.example.sport_forever.common.entity.TokenEntity;
import com.example.sport_forever.common.entity.UserEntity;
import com.example.sport_forever.common.repository.BanRepository;
import com.example.sport_forever.common.repository.TokenRepository;
import com.example.sport_forever.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenRepository tokenRepository;
    @Value("${jwt.secret_key}")
    private String secretKey;
    private final BanRepository banRepository;

    @Transactional
    public LoginResponseDto login(LoginDto loginDto) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByPhoneNumber(loginDto.phoneNumber());
        if (userEntity.isEmpty()) {
            throw new AccessDeniedException("전화번호가 일치하지 않습니다.");
        }

        if (!encoder.matches(loginDto.password(), userEntity.get().getPassword())) {
            throw new AccessDeniedException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = JwtUtil.generateToken(userEntity.get(), secretKey);
        String refreshToken = JwtUtil.createRefreshToken(secretKey);

        Optional<TokenEntity> tokenEntityOptional = tokenRepository.findTokenEntityByUserEntity(userEntity.get());
        TokenEntity tokenEntity = tokenEntityOptional.orElseGet(TokenEntity::new);

        tokenEntity.setUserEntity(userEntity.get());
        tokenEntity.setAccessToken(accessToken);
        tokenEntity.setRefreshToken(refreshToken);

        tokenRepository.save(tokenEntity);

        log.info("login : success - " + userEntity.get().getName());

        return new LoginResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public void signup(SignupDto signupDto) {
        String phoneNumber = signupDto.phoneNumber();
        userRepository.findUserEntityByPhoneNumber(phoneNumber);

        if (userRepository.findUserEntityByPhoneNumber(phoneNumber).isPresent()) {
            throw new SportException(CommunalResponse.ALREADY_SIGNUP_PHONENUMBER);
        }

        String password_encode = encoder.encode(signupDto.password());
        String name = signupDto.name();

        UserEntity userEntity = new UserEntity();
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setPassword(password_encode);
        userEntity.setName(name);
        userRepository.save(userEntity);
    }

    @Transactional
    public LoginResponseDto refreshToken(RefreshDto refreshDto) {
        if (JwtUtil.isExpired(refreshDto.refreshToken(), secretKey)) {
            throw new AccessDeniedException("refreshToken is expired");
        }

        String phoneNumber = refreshDto.phoneNumber();
        Optional<UserEntity> optionalUser = userRepository.findUserEntityByPhoneNumber(phoneNumber);

        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            Optional<TokenEntity> tokenEntity = tokenRepository.findTokenEntityByUserEntity(userEntity);
            if (tokenEntity.isEmpty()) {
                throw new AccessDeniedException("token not found");
            }

            if (Objects.equals(tokenEntity.get().getRefreshToken(), refreshDto.refreshToken())) {
                String accessToken = JwtUtil.generateToken(userEntity, secretKey);
                String refreshToken = JwtUtil.createRefreshToken(secretKey);
                tokenEntity.get().setAccessToken(accessToken);
                tokenEntity.get().setRefreshToken(refreshToken);
                tokenRepository.save(tokenEntity.get());

                return new LoginResponseDto(accessToken, refreshToken);
            }
            throw new AccessDeniedException("refreshToken is not correct");
        }
        throw new AccessDeniedException("user not found");
    }
}
