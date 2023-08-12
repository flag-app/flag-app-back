package com.flag.flag_back.Dto;

import com.flag.flag_back.Model.Day;
import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.Model.FlagMember;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FlagDto {
    private String name;
    private List<Day> dayList;
    private Integer minTime;
    private String place;
    private String memo;
    private Long userId;
    private List<FlagMember> friendsList;
    public Flag toEntity() {
        Flag flag = Flag.builder()
                .name(name)
                .dayList(dayList)
                .minTime(minTime)
                .place(place)
                .memo(memo)
                .userId(userId)
                .friendsList(friendsList)
                .build();
        return flag;
    }@Builder
    public FlagDto(String name, String cycle, List<Day> dayList, Integer minTime, String place, String memo, Long userId, List<FlagMember> friendsList) {
        this.name = name;
        this.dayList = dayList;
        this.minTime = minTime;
        this.place = place;
        this.memo = memo;
        this.userId = userId;
        this.friendsList = friendsList;
    }
}
