package com.degenerates.memium.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BadTokenException.class })
    protected ResponseEntity<Object> handleConflict(BadTokenException ex, WebRequest request) {
        log.error("Bad token rejected");
        String bodyOfResponse = "Bad Token";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = { OptionalEntityNotFoundException.class })
    protected ResponseEntity<Object> handleConflict(OptionalEntityNotFoundException ex, WebRequest request) {
        log.error("No Content for showing");
        String bodyOfResponse = "this optional entity has yet to be set or created";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }

    @ExceptionHandler(value = { AccessMismatchException.class })
    protected ResponseEntity<Object> handleConflict(AccessMismatchException ex, WebRequest request) {
        log.error("Not allowed to do so");
        String bodyOfResponse = "not authorised";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }


    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<Object> handleConflict(EntityNotFoundException ex, WebRequest request) {
        log.error("Entity was supposed to be found, but was not");
        String bodyOfResponse = "couldn't find entity";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { MultipartException.class })
    protected ResponseEntity<Object> handleConflict(MultipartException ex, WebRequest request) {
        log.error("Unsupported Media File provided");
        String bodyOfResponse = "not multipart";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    @ExceptionHandler(value = { NullPointerException.class })
    protected ResponseEntity<Object> handleConflict(NullPointerException ex, WebRequest request) {
        log.error("Unsupported Media File provided");
        String bodyOfResponse = "Bad input";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleConflict(IllegalArgumentException ex, WebRequest request) {
        log.error("Some data problem");
        String bodyOfResponse = "Bad input";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Json corrupted");
        String bodyOfResponse = "bad json, check enums etc.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
