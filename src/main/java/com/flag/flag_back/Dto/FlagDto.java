package com.flag.flag_back.Dto;

import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.Model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FlagDto {

    private Long id;
    private String name;
    private String sTime;
    private String eTime;
    private String cycle;
    private String userId;
    private List<User> friendsList;

    public Flag toEntity() {
        Flag flag = Flag.builder()
                .name(name)
                .sTime(sTime)
                .eTime(eTime)
                .cycle(cycle)
                .userId(userId)
                .friendsList(friendsList)
                .build();
        return flag;
    }

    @Builder
    public FlagDto(String name, String sTime, String eTime, String cycle, String userId, List<User> friendsList) {
        this.name = name;
        this.sTime = sTime;
        this.eTime = eTime;
        this.cycle = cycle;
        this.userId = userId;
        this.friendsList = friendsList;
    }
}
