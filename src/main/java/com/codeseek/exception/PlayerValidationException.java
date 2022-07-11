package com.codeseek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PlayerValidationException extends RuntimeException{

    public PlayerValidationException(String message) {
        super(message);
    }
}
