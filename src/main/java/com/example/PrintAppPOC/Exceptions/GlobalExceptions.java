package com.example.PrintAppPOC.Exceptions;

import com.example.PrintAppPOC.Responses.ErrorResponse;
import com.example.PrintAppPOC.Responses.NewUserResponse;
import com.example.PrintAppPOC.Responses.StatusResponse;
import io.jsonwebtoken.JwtException;
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
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(@NotNull ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CantCreateToken.class)
    public ResponseEntity<ErrorResponse> cantCreateToken(@NotNull CantCreateToken message) {
        return new ResponseEntity<>(new ErrorResponse(false, message.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExists(@NotNull UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
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
    public ResponseEntity<ErrorResponse> userNameNotValid(@NotNull UsernameConstraintException ex){
        return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage()),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> emptyToken(MissingRequestHeaderException ex){
        return new ResponseEntity<>(new ErrorResponse(false,ex.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> invalidToken(InvalidTokenException ex){
        return new ResponseEntity<>(new ErrorResponse(false,ex.getMessage()),HttpStatus.UNAUTHORIZED);
    }
}
