package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;


import lombok.Getter;
import lombok.Setter;


/**
 * Thrown when the application is unable to find data to calculate delivery fee.
 */
@Getter
@Setter
public class DeliveryFeeException extends RuntimeException {

  public DeliveryFeeException(String message) {
    super(message);
  }
}
