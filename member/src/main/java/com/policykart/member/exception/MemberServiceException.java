package com.policykart.member.exception;

import com.policykart.member.exception.error.ErrorType;
import lombok.Getter;

@Getter
public class MemberServiceException extends RuntimeException {
    private final String errorCode;
    private final ErrorType errorType;

    public MemberServiceException(ErrorType errorType, String message) {
        errorType.setErrorMessage(message);
        this.errorCode = errorType.name();
        this.errorType = errorType;
    }

    public MemberServiceException(ErrorType errorType) {
        this(errorType, errorType.getErrorMessage());
    }
}
