package com.codeseek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TeamValidationException  extends RuntimeException {

    public TeamValidationException(String message) {
        super(message);
    }
}
