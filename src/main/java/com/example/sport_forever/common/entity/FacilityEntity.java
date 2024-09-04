package com.example.sport_forever.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "facility")
public class FacilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long facilityId;

    @Column(nullable = false,  unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean reservationAble = true;

    public FacilityEntity(String name){
        this.name = name;
    }
}
