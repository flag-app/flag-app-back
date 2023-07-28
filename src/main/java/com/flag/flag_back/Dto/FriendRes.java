package com.flag.flag_back.Dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FriendRes {
    private long id;

    @Builder
    public FriendRes(long id) {
        this.id = id;
    }
}
