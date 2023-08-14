package com.flag.flag_back.Dto;

import com.flag.flag_back.Model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserInfo {
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,5}$", message = "닉네임은 특수문자를 제외한 2~5자리여야 합니다.")
    private String name;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    private String profile;

    public User toEntity() {
        User userEntity = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .profile(profile)
                .build();
        return userEntity;
    }

    @Builder
    public UserInfo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }
}
