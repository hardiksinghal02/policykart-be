package com.policykart.member.exception.error;

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
    CLAIM_NOT_FOUND("Claim not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("User not authorized to perform this action", HttpStatus.UNAUTHORIZED),
    INVALID_INPUT("Invalid Input", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("User Not found", HttpStatus.UNAUTHORIZED),
    POLICY_NOT_FOUND("Policy not found", HttpStatus.NOT_FOUND),
    SUBSCRIPTION_NOT_FOUND("Subscription not found", HttpStatus.NOT_FOUND),
    NOT_ELIGIBLE("Not eligible", HttpStatus.BAD_REQUEST),
    ;

    @Setter
    private String errorMessage;
    private final HttpStatus httpStatus;

    public ErrorType getErrorCode() {
        return this;
    }

}
