package com.example.sport_forever.common.repository;

import com.example.sport_forever.common.entity.FacilityEntity;
import com.example.sport_forever.common.entity.ReservationEntity;
import com.example.sport_forever.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    Optional<ReservationEntity> findByUserEntity(UserEntity userEntity);
    Optional<ReservationEntity> findByFacilityEntity(FacilityEntity facilityEntity);
}
