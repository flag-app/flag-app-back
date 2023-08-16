package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.CandidateRes;
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
import java.util.List;

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

    @DeleteMapping("/{flagId}")
    @Operation(summary = "flag 삭제", description = "flag를 삭제합니다.")
    public String deleteFlag(@PathVariable("flagId") Long flagId) {
        try {
            flagService.deleteFlag(flagId);
            return "Flag 삭제가 완료되었습니다.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{flagId}")
    @Operation(summary = "flag 수정", description = "flag 정보를 수정합니다.")
    public String updateFlag(@PathVariable("flagId") Long flagId, @RequestBody @Valid FlagDto flagDto) {
        try {
            System.out.print(flagDto);
            flagService.updateFlag(flagId, flagDto);
            return "Flag 수정이 완료되었습니다.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{flagId}/show")
    @Operation(summary = "flag 정보 조회", description = "총 인원 수, 되는 인원, 무응답 인원, 가능한 인원의 셀들을 반환합니다.")
    public FlagTimeTableRes getFlagTimeTable(@PathVariable("flagId") Long flagId) {
        return flagService.getFlagTimeTableRes(flagId);
    }

    @PostMapping("/{flagId}/candidate")
    @Operation(summary = "flag 후보 조회", description = "최소 시간을 만족하는 flag 후보를 반환합니다.")
    public List<CandidateRes> getCandidates(@PathVariable("flagId") Long flagId) {
        Flag flag = flagService.getFlag(flagId);

        if (flag != null) {
            return flagService.getCandidates(flag);
        }

        return null;
    }
}
