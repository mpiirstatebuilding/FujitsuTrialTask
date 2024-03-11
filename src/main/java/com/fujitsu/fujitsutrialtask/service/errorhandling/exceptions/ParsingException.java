package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/*
Thrown when encountering a problem during the parsing of new data.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParsingException extends Exception {
    private final String message;
}
