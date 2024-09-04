package com.example.sport_forever.common.repository;

import com.example.sport_forever.common.entity.RentalEntity;
import com.example.sport_forever.common.entity.UserEntity;
import com.example.sport_forever.common.entity.WareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
    List<RentalEntity> findByUserEntity(UserEntity userEntity);
    Optional<RentalEntity> findByUserEntityAndWareEntity(UserEntity userEntity, WareEntity wareEntity);

}
