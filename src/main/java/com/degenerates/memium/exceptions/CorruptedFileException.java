package com.degenerates.memium.exceptions;

public class CorruptedFileException extends RuntimeException {
    public CorruptedFileException() {
        super();
    }
    public CorruptedFileException(String message, Throwable cause) {
        super(message, cause);
    }
    public CorruptedFileException(String message) {
        super(message);
    }
    public CorruptedFileException(Throwable cause) {
        super(cause);
    }
}
