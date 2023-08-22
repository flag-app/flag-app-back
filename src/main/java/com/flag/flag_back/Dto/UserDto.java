package com.flag.flag_back.Dto;

import com.flag.flag_back.Model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {
    private long id;
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
    public UserDto(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
