package com.flag.flag_back.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /*user */
    INVALID_USER(false, 1001, "유효하지 않은 사용자입니다."),
    INVALID_PASSWORD(false, 1002, "비밀번호가 틀립니다."),
    LOGIN_ERROR(false, 1003, "로그인 중 오류가 발생했습니다."),
    JOIN_ERROR(false, 1004, "회원가입 중 오류가 발생했습니다."),
    USER_RETRIEVAL_ERROR(false, 1005, "사용자 정보를 가져오는 중 오류가 발생했습니다."),
    NICKNAME_CHANGE_ERROR(false, 1006, "닉네임 변경 중 오류가 발생했습니다."),
    PROFILE_CHANGE_ERROR(false, 1007, "프로필 변경 중 오류가 발생했습니다."),

    /*mail */
    TEMP_PASSWORD_SENT(true, 2001, "임시 비밀번호가 이메일로 전송되었습니다."),
    NO_REGISTERED_USER(false, 2002, "등록된 사용자가 없습니다."),

    /*friend */
    NICKNAME_SEARCH_ERROR(false, 3001, "닉네임 검색 중 오류가 발생했습니다."),
    ADD_FRIEND(true, 3002, "친구 추가 완료했습니다."),
    ALREADY_FRIEND(true, 3002, "이미 친구입니다."),
    ADD_FRIEND_ERROR(false, 3003, "친구 추가 중 오류가 발생했습니다."),



    /*flag*/
    ADD_GUEST(true, 4001, "GUEST가 초대된 FLAG 수락했습니다."),
    ADD_GUEST_ERROR(false, 3003, "QUEST 정보 입력 실패했습니다.");

    //1000 : 요청 성공

    //2000 : Request 오류
        //// Common
        //login

    //3000 : Response 오류
        // Common

    //4000 : Database, Server 오류

    //5000 :

    //6000 :

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}
