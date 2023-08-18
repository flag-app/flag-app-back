package com.flag.flag_back.Dto;

import lombok.Data;

import java.util.List;

@Data
public class FlagCellRes {

    private String date;
    private String startTime;
    private String endTime;
    private List<String> members;

    public FlagCellRes(String date, String startTime, String endTime, List<String> members) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.members = members;
    }
}
