package com.fujitsu.fujitsutrialtask.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "delivery-fee")
@ConfigurationPropertiesScan
public class DeliveryFeeServiceConfig {
    HashMap<String, String> cityToStation;
    HashMap<String, Float> rbfCity;
    HashMap<String, Float> rbfVehicle;
    List<Float> atef;
    List<Float> wsef;
    HashMap<String, Float> wpef;
}
