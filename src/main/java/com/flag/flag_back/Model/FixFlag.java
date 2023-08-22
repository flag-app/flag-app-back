package com.flag.flag_back.Model;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Getter
public class FixFlag {

    private String name;
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String place;
    private String memo;
    private List<String> members;

    public FixFlag(String name, LocalDate date, String startTime, String endTime, String place, String memo, List<String> members) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.memo = memo;
        this.members = members;
    }
}
