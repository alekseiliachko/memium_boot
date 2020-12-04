package com.degenerates.memium.exceptions;

public class AccessMismatchException extends RuntimeException {
    public AccessMismatchException() {
        super();
    }
    public AccessMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
    public AccessMismatchException(String message) {
        super(message);
    }
    public AccessMismatchException(Throwable cause) {
        super(cause);
    }
}