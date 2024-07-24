package com.example.PrintAppPOC.Exceptions;

import com.example.PrintAppPOC.Responses.NewUserResponse;
import com.example.PrintAppPOC.Responses.StatusResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
        List<String> errors = validationErrorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        String errorMessage = "[" + String.join(", ", errors) + "]";
        return new ResponseEntity<>(new StatusResponse(errorMessage,false), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StatusResponse> resourceNotFoundExceptionHandler( ResourceNotFoundException ex) {
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CantCreateToken.class)
    public ResponseEntity<StatusResponse> cantCreateToken( CantCreateToken message) {
        return new ResponseEntity<>(new StatusResponse(message.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StatusResponse> userAlreadyExists( UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new StatusResponse( ex.getMessage(),false), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MobileNumberValidationException.class)
    public ResponseEntity<StatusResponse> inValidMobileNumber(MobileNumberValidationException ex) {
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(NewUserTokenException.class)
    public ResponseEntity<NewUserResponse> newUSerResponse( NewUserTokenException ex){
        return new ResponseEntity<>(new NewUserResponse(true, ex.getMessage()),HttpStatus.OK);
    }
    @ExceptionHandler(UsernameConstraintException.class)
    public ResponseEntity<StatusResponse> userNameNotValid( UsernameConstraintException ex){
        return new ResponseEntity<>(new StatusResponse( ex.getMessage(),false),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<StatusResponse> emptyToken(MissingRequestHeaderException ex){
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<StatusResponse> invalidToken(InvalidTokenException ex){
        return new ResponseEntity<>(new StatusResponse(ex.getMessage(),false),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<StatusResponse> unknownException(UnknownException e){
        return new ResponseEntity<>(new StatusResponse(e.getMessage(),false),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<StatusResponse> jwtException(JwtException e){
        return new ResponseEntity<>(new StatusResponse(e.getMessage(),false),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(WrongFormatException.class)
    public ResponseEntity<StatusResponse> wrongFormatException(WrongFormatException e){
        return new ResponseEntity<>(new StatusResponse(e.getMessage(),false),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MissingParamException.class)
    public ResponseEntity<StatusResponse> missingParamException(MissingParamException e){
        return new ResponseEntity<>(new StatusResponse(e.getMessage(),false),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StoreDoesNotExist.class)
    public ResponseEntity<StatusResponse> storeDoesNotExist(Exception e){
        return new ResponseEntity<>(new StatusResponse("Store not associated",false),HttpStatus.UNAUTHORIZED);
    }
}
