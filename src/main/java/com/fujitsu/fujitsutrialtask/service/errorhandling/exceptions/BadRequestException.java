package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

  private final String message;
  private final HttpStatus httpStatus;
  private final ZonedDateTime zonedDateTime;
}
