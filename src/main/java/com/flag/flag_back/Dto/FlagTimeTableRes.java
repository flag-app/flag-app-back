package com.flag.flag_back.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class FlagTimeTableRes {

    private int timeSlot;
    private int userTotalCount;
    private List<String> dates;
    private List<String> acceptUsers;
    private List<String> nonResponseUsers;
    private List<Integer> ableCells;

    @Builder
    public FlagTimeTableRes(int timeSlot, int userTotalCount, List<String> dates, List<String> acceptUsers, List<String> nonResponseUsers, List<Integer> ableCells) {
        this.timeSlot = timeSlot;
        this.userTotalCount = userTotalCount;
        this.dates = dates;
        this.acceptUsers = acceptUsers;
        this.nonResponseUsers = nonResponseUsers;
        this.ableCells = ableCells;
    }
}
