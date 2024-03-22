package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Thrown when encountering a problem during the parsing of new data.
 */
@Getter
@Setter
public class ParsingException extends RuntimeException {

  public ParsingException(String message) {
    super(message);
  }
}
