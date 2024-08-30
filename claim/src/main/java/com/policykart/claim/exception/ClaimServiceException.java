package com.policykart.claim.exception;

import com.policykart.claim.exception.error.ErrorType;
import lombok.Getter;

@Getter
public class ClaimServiceException extends RuntimeException {
    private final String errorCode;
    private final ErrorType errorType;

    public ClaimServiceException(ErrorType errorType, String message) {
        errorType.setErrorMessage(message);
        this.errorCode = errorType.name();
        this.errorType = errorType;
    }

    public ClaimServiceException(ErrorType errorType) {
        this(errorType, errorType.getErrorMessage());
    }
}
