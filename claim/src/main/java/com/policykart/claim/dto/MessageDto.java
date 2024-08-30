package com.policykart.claim.dto;

import com.policykart.claim.exception.error.ErrorType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageDto {
    private ErrorType code;
    private String message;
}
