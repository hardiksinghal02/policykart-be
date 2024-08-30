package com.policykart.member.dto;

import com.policykart.member.exception.error.ErrorType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageDto {
    private ErrorType code;
    private String message;
}
