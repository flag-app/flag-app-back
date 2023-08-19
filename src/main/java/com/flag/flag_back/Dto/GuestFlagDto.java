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

    private Long guestId;
    private FlagStatus flagStatus;
    private List<Integer> possibleDates;

    @Builder
    public GuestFlagDto(Long guestId, FlagStatus flagStatus, List<Integer> possibleDates) {
        this.guestId = guestId;
        this.flagStatus = flagStatus;
        this.possibleDates = possibleDates;
    }
}
