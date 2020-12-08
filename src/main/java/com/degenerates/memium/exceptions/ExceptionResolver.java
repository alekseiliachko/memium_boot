package com.degenerates.memium.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ExceptionResolver extends ResponseEntityExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = { BadTokenException.class })
    protected ResponseEntity<Object> handleConflict(BadTokenException ex, WebRequest request) {
        String bodyOfResponse = "bad token";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { AccessMismatchException.class })
    protected ResponseEntity<Object> handleConflict(AccessMismatchException ex, WebRequest request) {
        String bodyOfResponse = "not authorised";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<Object> handleConflict(EntityNotFoundException ex, WebRequest request) {
        String bodyOfResponse = "couldn't find entity";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { MultipartException.class })
    protected ResponseEntity<Object> handleConflict(MultipartException ex, WebRequest request) {
        String bodyOfResponse = "not multipart";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
