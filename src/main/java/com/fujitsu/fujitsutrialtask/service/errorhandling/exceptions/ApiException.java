package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;


/*
  Thrown when the application is unable to get new weather data from the API.
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiException extends Exception {
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
