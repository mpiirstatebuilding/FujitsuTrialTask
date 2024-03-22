package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherConditionException extends Exception {
    public WeatherConditionException(String message) {
        super(message);
    }
}
