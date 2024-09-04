package com.example.sport_forever.common.repository;

import com.example.sport_forever.common.entity.TokenEntity;
import com.example.sport_forever.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findTokenEntityByUserEntity(UserEntity userEntity);
}
