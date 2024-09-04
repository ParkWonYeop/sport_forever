package com.example.sport_forever.common.entity;

import com.example.sport_forever.community.dto.PostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subject;

    @CreationTimestamp
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updateAt = LocalDateTime.now();

    public PostEntity(UserEntity user, PostDto postDto) {
        this.user = user;
        this.title = postDto.title();
        this.subject = postDto.subject();
    }
}
