package com.flag.flag_back.Dto;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class FlagRes2 {
    private long id;
    @Builder
    public FlagRes2(long id) {
        this.id = id;
    }
}