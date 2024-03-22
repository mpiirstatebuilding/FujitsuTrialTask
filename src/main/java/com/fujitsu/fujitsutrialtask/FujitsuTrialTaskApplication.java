package com.fujitsu.fujitsutrialtask;

import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.DeliveryFeeException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(DeliveryFeeException.class)
public class FujitsuTrialTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(FujitsuTrialTaskApplication.class, args);
    }

}
