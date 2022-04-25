package com.example.managementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementSystemApplication.class, args);
    }

}
