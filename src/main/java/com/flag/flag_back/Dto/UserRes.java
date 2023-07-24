package com.flag.flag_back.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserRes {
    private long id;

    @Builder
    public UserRes(long id) {
        this.id = id;
    }
}
