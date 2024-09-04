package com.example.sport_forever.common.repository;

import com.example.sport_forever.common.entity.BanEntity;
import com.example.sport_forever.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BanRepository extends JpaRepository<BanEntity, Long> {
    Optional<BanEntity> findByUserEntity(UserEntity userModel);
}
