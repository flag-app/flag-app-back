package com.flag.flag_back.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlagTimeTableRes {
    private int userCount;
    private List<Integer> ableCells;

    @Builder
    public FlagTimeTableRes(int userCount, List<Integer> ableCells) {
        this.userCount = userCount;
        this.ableCells = ableCells;
    }
}
