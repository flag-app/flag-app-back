package com.flag.flag_back.Dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequestDto {

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
