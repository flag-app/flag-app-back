package com.flag.flag_back.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlagTimeTableRes {
    private int userTotalCount;
    private List<String> acceptUsers;
    private List<String> nonResponseUsers;
    private List<Integer> ableCells;

    @Builder
    public FlagTimeTableRes(int userTotalCount, List<String> acceptUsers, List<String> nonResponseUsers, List<Integer> ableCells) {
        this.userTotalCount = userTotalCount;
        this.acceptUsers = acceptUsers;
        this.nonResponseUsers = nonResponseUsers;
        this.ableCells = ableCells;
    }
}
