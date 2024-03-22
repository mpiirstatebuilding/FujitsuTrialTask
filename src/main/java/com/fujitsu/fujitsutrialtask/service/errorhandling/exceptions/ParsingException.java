package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.*;

/*
Thrown when encountering a problem during the parsing of new data.
 */
@Getter
@Setter
public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }
}
