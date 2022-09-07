package com.mindhub.homebanking.exceptions;

import com.mindhub.homebanking.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExist(HttpServletRequest request,
                                                     EmailAlreadyExistException e) {
        return ResponseEntity.badRequest()
                .body(buildResponse(e, FORBIDDEN));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(HttpServletRequest request,
                                                               InvalidCredentialsException e) {
        return ResponseEntity.badRequest()
                .body(buildResponse(e, FORBIDDEN));
    }

    @ExceptionHandler(AccountLimitException.class)
    public ResponseEntity<?> handleAccountLimitException(HttpServletRequest request,
                                                         AccountLimitException e) {
        return ResponseEntity.badRequest()
                .body(buildResponse(e, FORBIDDEN));
    }

    @ExceptionHandler(CardColorException.class)
    public ResponseEntity<?> handleCardColorException(HttpServletRequest request,
                                                      CardColorException e) {
        return ResponseEntity.badRequest()
                .body(buildResponse(e, FORBIDDEN));
    }

    @ExceptionHandler(CardTypeException.class)
    public ResponseEntity<?> handleCardTypeException(HttpServletRequest request,
                                                     CardTypeException e) {
        return ResponseEntity.badRequest()
                .body(buildResponse(e, FORBIDDEN));
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleInvalidParameterException(HttpServletRequest request,
                                                             InvalidParameterException e) {
        return ResponseEntity.badRequest()
                .body(buildResponse(e, FORBIDDEN));
    }

    private ErrorResponse buildResponse(Exception e, HttpStatus httpStatus) {
        return new ErrorResponse(e, httpStatus.value());
    }
}
