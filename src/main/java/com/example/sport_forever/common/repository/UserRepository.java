package com.example.sport_forever.common.repository;


import com.example.sport_forever.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByPhoneNumber(String phoneNumber);
}
