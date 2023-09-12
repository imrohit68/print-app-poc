package com.example.PrintAppPOC.Exception;

import com.example.PrintAppPOC.Dtos.ErrorResponse;
import com.example.PrintAppPOC.Dtos.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CantCreateToken.class)
    public ResponseEntity<ErrorResponse> cantCreateToken(CantCreateToken message) {
        return new ResponseEntity<>(new ErrorResponse("error", message.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExists(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse("error", ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MobileNumberValidationException.class)
    public ResponseEntity<StatusResponse> inValidMobileNumber(MobileNumberValidationException ex) {
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false),HttpStatus.NOT_ACCEPTABLE);
    }
}
