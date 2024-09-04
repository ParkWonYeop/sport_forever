package com.example.sport_forever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EntityScan("com.example.sport_forever.common.entity;")
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SportForeverApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportForeverApplication.class, args);
    }

}
