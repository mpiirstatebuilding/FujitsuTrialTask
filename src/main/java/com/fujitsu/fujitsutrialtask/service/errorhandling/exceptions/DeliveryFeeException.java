package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryFeeException extends RuntimeException {
    public DeliveryFeeException(String message) {
        super(message);
    }
}
