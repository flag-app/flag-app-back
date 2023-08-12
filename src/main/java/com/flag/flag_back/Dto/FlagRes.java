package com.flag.flag_back.Dto;
import com.flag.flag_back.Model.Day;
import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
public class FlagRes {
    private long id;
    private String name;
    private String cycle;
    private List<Day> dayList;
    private Integer minTime;
    private String place;
    private String memo;
    @Builder
    public FlagRes(String name, String cycle, List<Day> dayList, Integer minTime, String place, String memo) {
        this.name = name;
        this.cycle = cycle;
        this.dayList = dayList;
        this.minTime = minTime;
        this.place = place;
        this.memo = memo;
    }
}