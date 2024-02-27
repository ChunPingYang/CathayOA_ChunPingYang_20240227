package com.cathybank.spring.data.mongodb.handler;

import com.cathybank.spring.data.mongodb.exception.InValidDateRangeException;
import com.cathybank.spring.data.mongodb.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(InValidDateRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidDateRangeException(InValidDateRangeException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorDetails());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
