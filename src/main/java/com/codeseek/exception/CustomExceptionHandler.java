package com.codeseek.exception;


import com.codeseek.common.Messages;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        ErrorAPI error = new ErrorAPI(Messages.RESOURCE_NOT_FOUND, ex.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorAPI error = new ErrorAPI(Messages.VALIDATION_FAILED, details.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ErrorAPI error = new ErrorAPI(Messages.FIELD_VALIDATION_FAILED, ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(TransferValidationException.class)
    public final ResponseEntity<Object> handleNTransferValidationException(TransferValidationException ex, WebRequest request) {
        ErrorAPI error = new ErrorAPI(Messages.TRANSFER_VALIDATION_EXCEPTION, ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(PlayerValidationException.class)
    public final ResponseEntity<Object> handlePlayerValidationException(PlayerValidationException ex, WebRequest request) {
        ErrorAPI error = new ErrorAPI(Messages.PLAYER_VALIDATION_EXCEPTION, ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

        @ExceptionHandler(TeamValidationException.class)
    public final ResponseEntity<Object> handleTeamValidationException(TeamValidationException ex, WebRequest request) {
        ErrorAPI error = new ErrorAPI(Messages.TEAM_VALIDATION_EXCEPTION, ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorAPI error = new ErrorAPI(Messages.FAILED_TO_CONVERT_VALUE, ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(error);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> handleNullPointerException(Exception ex, WebRequest request) {
        ErrorAPI error = new ErrorAPI(Messages.NULL_POINTER_EXCEPTION, ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllOtherExceptions(Exception ex, WebRequest request) {
        ErrorAPI error = new ErrorAPI(Messages.OTHER_EXCEPTION, ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
