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
@Entity(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private FacilityEntity facilityEntity;

    @Column(nullable = false)
    private LocalDateTime reservationTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Boolean activate = false;

    @CreationTimestamp
    private LocalDateTime createAt = LocalDateTime.now();

    public ReservationEntity(FacilityEntity facilityEntity, UserEntity userEntity, LocalDateTime reservationTime, LocalDateTime endTime) {
        this.reservationTime = reservationTime;
        this.userEntity = userEntity;
        this.endTime = endTime;
        this.facilityEntity = facilityEntity;
    }
}
