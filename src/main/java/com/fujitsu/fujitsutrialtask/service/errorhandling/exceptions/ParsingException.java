package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.Data;

/*
Thrown when encountering a problem during the parsing of new data.
 */
@Data
public class ParsingException extends Exception {
    private final String message;
}
