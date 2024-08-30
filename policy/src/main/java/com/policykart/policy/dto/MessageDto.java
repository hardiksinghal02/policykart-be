package com.policykart.policy.dto;

import com.policykart.policy.exception.error.ErrorType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageDto {
    private ErrorType code;
    private String message;
}
