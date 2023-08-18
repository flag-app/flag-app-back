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
    private Integer timeSlot;
    private Integer minTime;
    private String place;
    private String memo;
    private Long hostId;
    private List<String> dates;
    private List<Long> guestId;
    private List<Integer> possibleDates;
    public Flag toEntity() {
        Flag flag = Flag.builder()
                .name(name)
                .minTime(minTime)
                .place(place)
                .memo(memo)
                .build();
        return flag;
    }@Builder
    public FlagDto(String name, Integer timeSlot, Integer minTime, String place, String memo, Long hostId, List<String> dates, List<Long> guestId, List<Integer> possibleDates) {
        this.name = name;
        this.timeSlot = timeSlot;
        this.minTime = minTime;
        this.place = place;
        this.memo = memo;
        this.hostId = hostId;
        this.dates = dates;
        this.guestId = guestId;
        this.possibleDates = possibleDates;
    }
}
