package com.example.sport_forever.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rental")
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentalId;

    @ManyToOne
    @JoinColumn(name = "ware_id", nullable = false)
    private WareEntity wareEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false)
    private LocalDateTime rentalTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Boolean activate = false;

    @CreationTimestamp
    private LocalDateTime createAt = LocalDateTime.now();

    public RentalEntity(WareEntity wareModel, UserEntity userModel, LocalDateTime rentalTime, LocalDateTime endTime) {
        this.wareEntity = wareModel;
        this.userEntity = userModel;
        this.rentalTime = rentalTime;
        this.endTime = endTime;
    }
}
