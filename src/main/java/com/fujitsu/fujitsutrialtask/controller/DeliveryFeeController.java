package com.fujitsu.fujitsutrialtask.controller;

import com.fujitsu.fujitsutrialtask.service.DeliveryFeeService;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.DeliveryFeeException;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.WeatherConditionException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@Slf4j
public class DeliveryFeeController {
    final DeliveryFeeService deliveryFeeService;

    /**
     * Return delivery fee based on parameters provided by client.
     *
     * @param city - required, permitted cities can be viewed in application.properties
     * @param vehicle - required, permitted vehicles can be viewed in application.properties
     * @param timestamp - optional, UNIX timestamp in seconds
     *
     * @return delivery fee calculated from input
     */
    @GetMapping("/api/deliveryfee")
    public Float getDeliveryFee(@RequestParam(name = "city") String city,
        @RequestParam(name = "vehicle") String vehicle,
        @RequestParam(required = false, name = "timestamp") String timestamp)
        throws WeatherConditionException, DeliveryFeeException {
        log.info("Request received.");
        return deliveryFeeService.getDeliveryFee(city, vehicle, timestamp);
    }
}
