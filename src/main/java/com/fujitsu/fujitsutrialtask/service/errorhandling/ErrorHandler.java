package com.fujitsu.fujitsutrialtask.service.errorhandling;


import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryParameterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ApiException.class, ParsingException.class})
    public void handleWeatherDataServiceExceptions(Exception e) {
        log.error("Failed to update weather data. Cause: " + e);
    }

    @ExceptionHandler({DeliveryFeeException.class, WeatherConditionException.class, QueryParameterException.class})
    public ResponseEntity<Object> handleDeliveryFeeServiceExceptions(Exception e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        log.error("Failed to provide delivery fee. Cause: " + e);
        BadRequestException badRequestException = new BadRequestException(e.getMessage(), badRequest, ZonedDateTime.now());
        return new ResponseEntity<>(badRequestException, new HttpHeaders(), badRequest);
    }
}
