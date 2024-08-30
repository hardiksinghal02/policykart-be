package com.policykart.auth.exception;

import com.policykart.auth.exception.error.ErrorType;
import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException {
    private final String errorCode;
    private final ErrorType errorType;

    public AuthServiceException(ErrorType errorType, String message) {
        errorType.setErrorMessage(message);
        this.errorCode = errorType.name();
        this.errorType = errorType;
    }

    public AuthServiceException(ErrorType errorType) {
        this(errorType, errorType.getErrorMessage());
    }
}
