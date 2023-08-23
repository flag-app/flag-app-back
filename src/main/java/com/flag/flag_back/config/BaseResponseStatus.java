package com.flag.flag_back.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /*Common */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    INVALID_AUTHORIZATION_CODE(false, 1001, "유효하지 않은 Authorization입니다."),

    /*user */
    INVALID_USER(false, 1002, "등록되지 않은 사용자입니다."),
    INVALID_PASSWORD(false, 1003, "비밀번호가 틀립니다."),
    LOGIN_ERROR(false, 1004, "로그인 중 오류가 발생했습니다."),
    JOIN_ERROR(false, 1005, "회원가입 중 오류가 발생했습니다."),
    NICKNAME_CHANGE_ERROR(false, 1006, "닉네임 변경 중 오류가 발생했습니다."),
    PROFILE_SUCCESS(true, 1007, "프로필 변경에 성공했습니다."),
    PROFILE_CHANGE_ERROR(false, 1008, "프로필 변경 중 오류가 발생했습니다."),
    PASSWORD_CHANGE_SUCCESS(true, 1009, "비밀번호 변경에 성공했습니다."),
    PASSWORD_CHANGE_FAILURE(false, 1010, "비밀번호 변경에 실패했습니다."),
    NICKNAME_ALREADY_EXISTS(false, 1011, "이미 존재하는 닉네임입니다."),
    NICKNAME_AVAILABLE(true, 1012, "사용 가능한 닉네임입니다."),
    EMAIL_ALREADY_EXISTS(false, 1013, "이미 존재하는 이메일입니다."),
    EMAIL_AVAILABLE(true, 1014, "사용 가능한 이메일입니다."),
    MEMBERSHIP_WITHDRAWAL_SUCCESS(true, 1015, "회원 탈퇴에 성공했습니다."),

    /*mail */
    TEMP_PASSWORD_SENT(true, 2001, "임시 비밀번호가 이메일로 전송되었습니다."),

    /*friend */
    NICKNAME_SEARCH_ERROR(false, 3001, "닉네임 검색 중 오류가 발생했습니다."),
    ADD_FRIEND(true, 3002, "친구 추가 완료했습니다."),
    ALREADY_FRIEND(true, 3002, "이미 친구입니다."),
    ADD_FRIEND_ERROR(false, 3003, "친구 추가 중 오류가 발생했습니다."),
    NICKNAME_NOT_EXISTS(false, 3004, "존재하지 않는 닉네임입니다."),
    DO_NOT_SELF_ADD(false, 3005, "자기 자신을 친구 추가하지 마세요."),

    /*flag*/
    FLAG_CREATE_SUCCESS(true, 4001, "플래그 생성이 완료되었습니다."),
    FLAG_CREATE_FAIL(false, 4002, "플래그 생성이 실패했습니다."),
    FLAG_DELETE_SUCCESS(true, 4003, "플래그 삭제에 성공했습니다."),
    FLAG_DELETE_FAIL(true, 4004, "플래그 삭제에 실패했습니다."),
    FLAG_UPDARE_SUCCESS(true, 4005, "플래그 수정에 성공했습니다."),
    FLAG_UPDARE_FAIL(true, 4006, "플래그 수정에 실패했습니다."),

    /*ufm*/
    ADD_GUEST(true, 5001, "GUEST가 초대된 FLAG 수락했습니다."),
    ADD_GUEST_ERROR(false, 5002, "QUEST 정보 입력 실패했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}


