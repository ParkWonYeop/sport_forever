package com.example.sport_forever.user;

import com.example.sport_forever.common.controller.SportException;
import com.example.sport_forever.common.controller.constant.CommunalResponse;
import com.example.sport_forever.common.entity.BanEntity;
import com.example.sport_forever.common.entity.UserEntity;
import com.example.sport_forever.common.repository.BanRepository;
import com.example.sport_forever.common.repository.UserRepository;
import com.example.sport_forever.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.sport_forever.common.utils.SecurityUtil.getCurrentMemberId;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BanRepository banRepository;
    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo() {
        Optional<UserEntity> optionalUserModel = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());

        if(optionalUserModel.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userModel = optionalUserModel.get();

        Optional<BanEntity> optionalBanModel = banRepository.findByUserEntity(userModel);

        long banCount = 0;
        LocalDateTime banTime = null;

        if(optionalBanModel.isPresent()) {
            BanEntity banModel = optionalBanModel.get();
            banCount = banModel.getBanCount();
            banTime = banModel.getBanTime();
        }

        return new UserResponseDto(userModel.getPhoneNumber(), userModel.getName(), userModel.getPermission(), banCount, banTime);
    }
}
