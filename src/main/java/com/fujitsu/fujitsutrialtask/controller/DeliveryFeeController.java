package com.fujitsu.fujitsutrialtask.controller;

import com.fujitsu.fujitsutrialtask.service.DeliveryFeeService;
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

    @GetMapping("/api/deliveryfee")
    public Float getDeliveryFee(@RequestParam(name = "city") String city, @RequestParam(name = "vehicle") String vehicle,
                                 @RequestParam(required = false, name = "timestamp") String timestamp) {
        return deliveryFeeService.getDeliveryFee(city, vehicle, timestamp);
    }
}
