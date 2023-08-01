package com.flag.flag_back.Dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FlagRes {
    private long id;

    @Builder
    public FlagRes(long id) {
        this.id = id;
    }
}
