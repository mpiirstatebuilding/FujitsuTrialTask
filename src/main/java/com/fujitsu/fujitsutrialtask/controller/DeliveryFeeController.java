package com.fujitsu.fujitsutrialtask.controller;

import com.fujitsu.fujitsutrialtask.service.DeliveryFeeService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
public class DeliveryFeeController {
    final DeliveryFeeService deliveryFeeService;

    @GetMapping("/api/deliveryfee")
    public String getDeliveryFee(@RequestParam(name = "city") String city, @RequestParam(name = "vehicle") String vehicle,
                                 @RequestParam(required = false, name = "timestamp") String timestamp) {
        return deliveryFeeService.getDeliveryFee(city, vehicle, timestamp);
    }
}
