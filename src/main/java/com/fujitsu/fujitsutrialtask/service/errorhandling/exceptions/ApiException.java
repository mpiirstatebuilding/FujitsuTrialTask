package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatusCode;


/*
  Thrown when the application is unable to get new weather data from the API.
 */
@Data
public class ApiException extends Exception {
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
