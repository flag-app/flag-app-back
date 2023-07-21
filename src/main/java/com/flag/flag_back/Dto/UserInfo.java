package com.flag.flag_back.Dto;

import com.flag.flag_back.Model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserInfo {
    private String name;
    private String email;
    private String password;

    public User toEntity() {
        User userEntity = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        return userEntity;
    }

    @Builder
    public UserInfo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
