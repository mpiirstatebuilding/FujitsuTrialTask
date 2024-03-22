package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Thrown when weather is not suitable for vehicle type.
 */
@Getter
@Setter
public class WeatherConditionException extends Exception {

  public WeatherConditionException(String message) {
    super(message);
  }
}
