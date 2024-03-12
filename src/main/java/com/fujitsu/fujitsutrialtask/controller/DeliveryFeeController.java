package com.fujitsu.fujitsutrialtask.controller;

import com.fujitsu.fujitsutrialtask.service.DeliveryFeeService;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
public class DeliveryFeeController {
    final DeliveryFeeService deliveryFeeService;
}
