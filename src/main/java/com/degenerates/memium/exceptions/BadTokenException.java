package com.degenerates.memium.exceptions;

public class BadTokenException extends RuntimeException {
    public BadTokenException() {
        super();
    }
    public BadTokenException(String message, Throwable cause) {
        super(message, cause);
    }
    public BadTokenException(String message) {
        super(message);
    }
    public BadTokenException(Throwable cause) {
        super(cause);
    }
}
