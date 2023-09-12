package com.example.PrintAppPOC.Exceptions;

import com.example.PrintAppPOC.Responses.ErrorResponse;
import com.example.PrintAppPOC.Responses.NewUserResponse;
import com.example.PrintAppPOC.Responses.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CantCreateToken.class)
    public ResponseEntity<ErrorResponse> cantCreateToken(CantCreateToken message) {
        return new ResponseEntity<>(new ErrorResponse(false, message.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExists(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MobileNumberValidationException.class)
    public ResponseEntity<StatusResponse> inValidMobileNumber(MobileNumberValidationException ex) {
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(NewUserTokenException.class)
    public ResponseEntity<NewUserResponse> newUSerResponse(NewUserTokenException ex){
        return new ResponseEntity<>(new NewUserResponse(true, ex.getMessage()),HttpStatus.OK);
    }
}
