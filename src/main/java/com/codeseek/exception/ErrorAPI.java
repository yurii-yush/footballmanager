package com.codeseek.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorAPI {

    private String message;
    private String details;
}
