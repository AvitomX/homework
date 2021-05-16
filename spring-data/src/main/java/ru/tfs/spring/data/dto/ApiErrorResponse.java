package ru.tfs.spring.data.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiErrorResponse {
    private final String errorCode;
    private final String errorMessage;

    public ApiErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
