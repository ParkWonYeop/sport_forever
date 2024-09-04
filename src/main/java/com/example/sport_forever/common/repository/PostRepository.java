package com.example.sport_forever.common.repository;

import com.example.sport_forever.common.entity.PostEntity;
import com.example.sport_forever.common.entity.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByUser(UserEntity user, PageRequest pageRequest);
    Optional<PostEntity> findByPostId(Long id);
}
