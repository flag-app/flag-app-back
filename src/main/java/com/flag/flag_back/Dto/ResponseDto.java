package com.flag.flag_back.Dto;

import org.springframework.http.HttpStatus;

public class ResponseDto<T> {
    private boolean success;
    private String message;
    private T data;

    public ResponseDto(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>(true, message, data);
    }

    public static <T> ResponseDto<T> fail(String message, T data) {
        return new ResponseDto<>(false, message, data);
    }

    public static <T> ResponseDto<T> fail(HttpStatus status, String message, T data) {
        return new ResponseDto<>(false, message, data);
    }
}
