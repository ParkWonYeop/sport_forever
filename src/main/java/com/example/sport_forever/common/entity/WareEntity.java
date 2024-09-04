package com.example.sport_forever.common.entity;

import com.example.sport_forever.common.enums.WareEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ware")
public class WareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wareId;

    @Column(nullable = false)
    private WareEnum category;

    @Column(nullable = false,  unique = true)
    private String name;

    @Column(nullable = false)
    private long maxCount;

    @Column(nullable = false)
    private long currentCount;

    public WareEntity(WareEnum category, String name, long maxCount) {
        this.category = category;
        this.name = name;
        this.maxCount = maxCount;
        this.currentCount = maxCount;
    }
}
