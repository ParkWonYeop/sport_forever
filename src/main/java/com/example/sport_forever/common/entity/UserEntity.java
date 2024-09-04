package com.example.sport_forever.common.entity;

import com.example.sport_forever.common.enums.PermissionEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private PermissionEnum permission = PermissionEnum.USER;

    @CreationTimestamp
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updateAt = LocalDateTime.now();
}
