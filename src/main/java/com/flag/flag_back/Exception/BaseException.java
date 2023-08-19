package com.flag.flag_back.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseException extends Throwable {
    private final int code;
    private final String message;

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
