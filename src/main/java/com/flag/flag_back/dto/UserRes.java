package com.flag.flag_back.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserRes {
    private long id;

    @Builder
    public UserRes(long id) {
        this.id = id;
    }
}
