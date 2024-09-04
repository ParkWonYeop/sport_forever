package com.example.sport_forever.common.repository;

import com.example.sport_forever.common.entity.CommentEntity;
import com.example.sport_forever.common.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPost(PostEntity post);
}
