package com.policykart.policy.exception;

import com.policykart.policy.exception.error.ErrorType;
import lombok.Getter;

@Getter
public class PolicyServiceException extends RuntimeException {
    private final String errorCode;
    private final ErrorType errorType;

    public PolicyServiceException(ErrorType errorType, String message) {
        errorType.setErrorMessage(message);
        this.errorCode = errorType.name();
        this.errorType = errorType;
    }

    public PolicyServiceException(ErrorType errorType) {
        this(errorType, errorType.getErrorMessage());
    }
}
