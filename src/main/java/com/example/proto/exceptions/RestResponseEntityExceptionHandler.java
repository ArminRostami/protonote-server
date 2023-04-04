package com.example.proto.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameExistsException.class)
    protected ResponseEntity<Object> handleUserExistsException(RuntimeException ex, WebRequest req) {
        String body = "Username already exists";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, req);
    }

    @ExceptionHandler(UsernameTooShortException.class)
    protected ResponseEntity<Object> handleUsernameTooShortException(RuntimeException ex, WebRequest req) {
        String body = "Username must be at least 4 characters";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(PasswordTooShortException.class)
    protected ResponseEntity<Object> handlePasswordTooShortException(RuntimeException ex, WebRequest req) {
        String body = "Password must be at least 4 characters";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundExcetion(RuntimeException ex, WebRequest req) {
        String body = "Unable to share: Username not found";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUndefinedExceptions(RuntimeException ex, WebRequest req) {
        String body = "An Error ocurred";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
    }

}