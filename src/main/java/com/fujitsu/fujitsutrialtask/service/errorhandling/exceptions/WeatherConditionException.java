package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WeatherConditionException extends Exception {
    public WeatherConditionException(String message) {
        super(message);
    }
}
