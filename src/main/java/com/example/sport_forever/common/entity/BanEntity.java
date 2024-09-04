package com.example.sport_forever.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_ban")
public class BanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long banId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false)
    private long banCount;

    @Column(nullable = false)
    private LocalDateTime banTime;

    public BanEntity(UserEntity userModel) {
        this.userEntity = userModel;
        this.banCount = 1;
        this.banTime = LocalDateTime.now().plusDays(1);
    }
}
