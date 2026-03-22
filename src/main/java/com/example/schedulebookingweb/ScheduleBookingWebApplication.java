package com.example.schedulebookingweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduleBookingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleBookingWebApplication.class, args);
    }

}
