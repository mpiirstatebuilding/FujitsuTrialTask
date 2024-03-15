package com.fujitsu.fujitsutrialtask.service.errorhandling;


import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.ApiException;
import com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions.ParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ApiException.class, ParsingException.class})
    public ResponseEntity<Object> handleWeatherDataServiceExceptions(Exception e) {
        return new ResponseEntity<>(, new HttpHeaders())
    }
}
