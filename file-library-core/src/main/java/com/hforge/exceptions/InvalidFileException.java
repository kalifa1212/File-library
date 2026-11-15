package com.hforge.exceptions;

import lombok.Getter;

import java.util.List;

public class InvalidFileException extends RuntimeException {
    @Getter
    private ErrorCodes errorCode;
    @Getter
    private List<String> errors;

    public InvalidFileException(String message) {
        super(message);
    }
    public InvalidFileException(String message,Throwable cause) {
        super(message,cause);
    }
    public InvalidFileException(String message,Throwable cause, ErrorCodes errorCodes) {
        super(message,cause);
        this.errorCode=errorCodes;
    }
    public InvalidFileException(String message, ErrorCodes errorCodes) {
        super(message);
        this.errorCode=errorCodes;

    }
    public InvalidFileException(String message, ErrorCodes errorCodes, List<String> errors) {
        super(message);
        this.errorCode=errorCodes;
        this.errors=errors;
    }
}
