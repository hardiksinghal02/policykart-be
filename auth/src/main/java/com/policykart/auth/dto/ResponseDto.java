package com.policykart.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.policykart.auth.exception.error.ErrorType;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private final Date timestamp = new Date();
    private final MessageDto error;

    @SuppressWarnings("all")
    public static <T> ResponseDto<T> success(T data) {
        return (ResponseDto<T>) ResponseDto.builder().success(true).data(data).build();
    }

    @SuppressWarnings("all")
    public static <T> ResponseDto<T> failure(ErrorType e) {
        return (ResponseDto<T>)
                ResponseDto.builder()
                        .success(false)
                        .error(
                                MessageDto.builder()
                                        .code(e)
                                        .message(e.getErrorMessage())
                                        .build()
                        )
                        .build();
    }

    @SuppressWarnings("all")
    public static <T> ResponseDto<T> failure(Exception e) {
        return (ResponseDto<T>)
                ResponseDto.builder()
                        .success(false)
                        .error(MessageDto.builder()
                                .code(ErrorType.SOMETHING_WENT_WRONG)
                                .message(e.getMessage())
                                .build()
                        )
                        .build();
    }
}
