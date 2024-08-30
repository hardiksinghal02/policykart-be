package com.policykart.policy.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorType {

    TOKEN_EXPIRED("Token Expired", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("Invalid Token", HttpStatus.UNAUTHORIZED),
    SOMETHING_WENT_WRONG("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT("Invalid Input", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("User Not found", HttpStatus.UNAUTHORIZED),
    POLICY_NOT_FOUND("Policy not found", HttpStatus.NOT_FOUND),
    PROVIDER_NOT_FOUND("Provider not found", HttpStatus.NOT_FOUND),
    ;

    @Setter
    private String errorMessage;
    private final HttpStatus httpStatus;

    public ErrorType getErrorCode() {
        return this;
    }

}
