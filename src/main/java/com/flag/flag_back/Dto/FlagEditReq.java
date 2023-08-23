package com.flag.flag_back.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlagEditReq {

    private String name;
    private String place;
    private String memo;

    public FlagEditReq(String name, String place, String memo) {
        this.name = name;
        this.place = place;
        this.memo = memo;
    }
}
