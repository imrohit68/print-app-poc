package com.example.PrintAppPOC.Exception;

import com.example.PrintAppPOC.Dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return new ResponseEntity<>(new ErrorResponse("error", ex.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CantCreateToken.class)
    public ResponseEntity<ErrorResponse> cantCreateToken(CantCreateToken message){
        return new ResponseEntity<>(new ErrorResponse("error",message.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
