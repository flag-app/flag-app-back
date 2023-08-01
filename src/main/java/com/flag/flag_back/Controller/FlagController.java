package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.FlagDto;
import com.flag.flag_back.Dto.FlagRes;
import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.service.FlagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("flag")
public class FlagController {
    private final FriendController friendController;
    private final FlagService flagService;

    @GetMapping("/add")
    public FlagRes create(@RequestParam(value="flagDto") FlagDto flagDto) {
        try {
            Flag flag = new Flag();
            flag.setName(flagDto.getName());
            flag.setSTime(flagDto.getSTime());
            flag.setETime(flagDto.getETime());
            flag.setCycle(flagDto.getCycle());
            flag.setUserId(flagDto.getUserId());
            flag.setFriendsList(flagDto.getFriendsList());

            Long id = flagService.createFlag(flag);
            return new FlagRes(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
