package com.example.sport_forever.common.repository;

import com.example.sport_forever.common.entity.WareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WareRepository extends JpaRepository<WareEntity, Long> {
    Optional<WareEntity> findByName(String name);
}

