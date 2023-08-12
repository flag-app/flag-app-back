package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.FlagDto;
import com.flag.flag_back.Dto.FlagRes2;
import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.service.FlagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("flag")
public class FlagController {
    private final FriendController friendController;
    private final FlagService flagService;

    @PostMapping("/add")
    public FlagRes2 create(@RequestBody @Valid FlagDto flagDto) {
        try {
            Flag flag = new Flag();
            flag.setName(flagDto.getName());
            flag.setDayList(flagDto.getDayList());
            flag.setMinTime(flagDto.getMinTime());
            flag.setPlace(flagDto.getPlace());
            flag.setMemo(flagDto.getMemo());
            flag.setUserId(flagDto.getUserId());
            flag.setFriendsList(flagDto.getFriendsList());

            Long id = flagService.createFlag(flag);
            return new FlagRes2(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
