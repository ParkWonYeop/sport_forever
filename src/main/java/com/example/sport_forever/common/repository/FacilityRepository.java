package com.example.sport_forever.common.repository;

import com.example.sport_forever.common.entity.FacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<FacilityEntity, Long> {
    Optional<FacilityEntity> findByName(String name);
}
