package com.policykart.auth.dto;

import com.policykart.auth.exception.error.ErrorType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageDto {
    private ErrorType code;
    private String message;
}
