package com.flag.flag_back.Dto;

import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.Model.FlagStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GuestFlagDto {

    private List<Integer> possibleDates;

    @Builder
    public GuestFlagDto(List<Integer> possibleDates) {
        this.possibleDates = possibleDates;
    }
}
