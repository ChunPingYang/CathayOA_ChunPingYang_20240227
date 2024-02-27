package com.cathybank.spring.data.mongodb.exception;

import com.cathybank.spring.data.mongodb.util.ErrorDetails;

public class InValidDateRangeException extends RuntimeException{

    private final ErrorDetails errorDetails;

    public InValidDateRangeException(ErrorDetails errorDetails){
        super(errorDetails.getMessage());
        this.errorDetails = errorDetails;
    }

    public ErrorDetails getErrorDetails(){
        return errorDetails;
    }
}
