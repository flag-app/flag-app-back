package com.flag.flag_back.Dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String name;
    private String email;

    private boolean existFriend;

    public UserResponse() {
    }

    @Builder
    public UserResponse(long id, String name, String email, boolean existFriend) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.existFriend = existFriend;
    }


}
