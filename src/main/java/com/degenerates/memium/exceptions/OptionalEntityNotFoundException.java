package com.degenerates.memium.exceptions;

public class OptionalEntityNotFoundException extends RuntimeException {
    public OptionalEntityNotFoundException() {
        super();
    }
    public OptionalEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public OptionalEntityNotFoundException(String message) {
        super(message);
    }
    public OptionalEntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
