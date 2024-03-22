package com.fujitsu.fujitsutrialtask.service.errorhandling.exceptions;

import lombok.*;

/*
Thrown when encountering a problem during the parsing of new data.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ParsingException extends Exception {
    private final String message;
}
