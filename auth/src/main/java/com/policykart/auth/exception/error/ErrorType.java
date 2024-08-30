package com.policykart.auth.exception.error;

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
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    INCORRECT_PASSWORD("Incorrect Password", HttpStatus.UNAUTHORIZED),
    USER_ALREADY_EXISTS("User already exists", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL("Invalid Email", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("Invalid credentials", HttpStatus.BAD_REQUEST),
    ;

    @Setter
    private String errorMessage;
    private final HttpStatus httpStatus;
}
