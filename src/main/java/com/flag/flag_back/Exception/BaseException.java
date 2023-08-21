package com.flag.flag_back.Exception;
import com.flag.flag_back.config.BaseResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseException extends Exception {
    private BaseResponseStatus status;  //BaseResoinseStatus 객체에 매핑

//    private boolean success;
//    private String message;
//    private String token; // 예를 들어 토큰을 추가하는 경우
//
//    public BaseException(BaseResponseStatus status) {
//        this.status = status;
//    }
//
//    // 생성자 추가
//    public BaseException(BaseResponseStatus status, String message) {
//        super(message);
//        this.status = status;
//    }
//
//    // 생성자 추가
//    public BaseException(BaseResponseStatus status, String message, String token) {
//        super(message);
//        this.status = status;
//        this.token = token;
//    }
//    public BaseException(boolean success, String message, String token) {
//        this.success = success;
//        this.message = message;
//        this.token = token;
//    }

}
