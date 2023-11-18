package com.example.PrintAppPOC.Exceptions;

import com.example.PrintAppPOC.Responses.NewUserResponse;
import com.example.PrintAppPOC.Responses.StatusResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StatusResponse> resourceNotFoundExceptionHandler(@NotNull ResourceNotFoundException ex) {
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CantCreateToken.class)
    public ResponseEntity<StatusResponse> cantCreateToken(@NotNull CantCreateToken message) {
        return new ResponseEntity<>(new StatusResponse(message.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StatusResponse> userAlreadyExists(@NotNull UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new StatusResponse( ex.getMessage(),false), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MobileNumberValidationException.class)
    public ResponseEntity<StatusResponse> inValidMobileNumber(@NotNull MobileNumberValidationException ex) {
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(NewUserTokenException.class)
    public ResponseEntity<NewUserResponse> newUSerResponse(@NotNull NewUserTokenException ex){
        return new ResponseEntity<>(new NewUserResponse(true, ex.getMessage()),HttpStatus.OK);
    }
    @ExceptionHandler(UsernameConstraintException.class)
    public ResponseEntity<StatusResponse> userNameNotValid(@NotNull UsernameConstraintException ex){
        return new ResponseEntity<>(new StatusResponse( ex.getMessage(),false),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<StatusResponse> emptyToken(MissingRequestHeaderException ex){
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<StatusResponse> invalidToken(InvalidTokenException ex){
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<StatusResponse> unknownException(UnknownException e){
        return new ResponseEntity<>(new StatusResponse(e.getMessage(),false),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
