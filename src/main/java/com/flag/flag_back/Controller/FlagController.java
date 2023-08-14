package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.FlagDto;
import com.flag.flag_back.Dto.FlagRes2;
import com.flag.flag_back.Dto.FlagTimeTableRes;
import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.Model.FlagRole;
import com.flag.flag_back.Model.FlagStatus;
import com.flag.flag_back.Model.UserFlagManager;
import com.flag.flag_back.service.FlagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("flag")
public class FlagController {

    private final FlagService flagService;

    @PostMapping("/add")
    @Operation(summary = "flag 생성", description = "flag 생성합니다.")
    public String createFlag(@RequestBody @Valid FlagDto flagDto) {
        try {
            flagService.createFlag(flagDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/flag";
    }

    @PostMapping("/{flagId}/show")
    public FlagTimeTableRes getFlagTimeTable(@PathVariable @RequestParam Long flagId) {
        Flag flag = flagService.getFlag(flagId);

        if (flag != null) {
            return new FlagTimeTableRes(flagService.getUserCount(flag), flagService.getCellIndexes(flag));
        }

        return null;
    }
}
