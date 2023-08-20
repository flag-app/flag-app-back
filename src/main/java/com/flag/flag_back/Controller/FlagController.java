package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.CandidateRes;
import com.flag.flag_back.Dto.FlagCellRes;
import com.flag.flag_back.Dto.FlagDto;
import com.flag.flag_back.Dto.FlagTimeTableRes;
import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.jwt.JwtTokenProvider;
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
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    @Operation(summary = "flag 생성", description = "flag 생성합니다.")
    public String createFlag(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody @Valid FlagDto flagDto) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            flagService.createFlag(flagDto, user);
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
    @Operation(summary = "flag 수정", description = "flag 정보((이름,장소,메모)를 수정합니다.")
    public String updateFlag(@PathVariable("flagId") Long flagId, @RequestBody @Valid FlagDto flagDto) {
        try {
            System.out.print(flagDto);
            flagService.updateFlag(flagId, flagDto);
            return "Flag 수정이 완료되었습니다.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{flagId}/show")
    @Operation(summary = "flag 정보 조회", description = "총 인원 수, 되는 인원, 무응답 인원, 가능한 인원의 셀들을 반환합니다.")
    public FlagTimeTableRes getFlagTimeTable(@PathVariable("flagId") Long flagId) {
        return flagService.getFlagTimeTableRes(flagId);
    }

    @GetMapping("/{flagId}/candidate")
    @Operation(summary = "flag 후보 조회", description = "최소 시간을 만족하는 flag 후보를 반환합니다.")
    public List<CandidateRes> getCandidates(@PathVariable("flagId") Long flagId) {
        Flag flag = flagService.getFlag(flagId);

        if (flag != null) {
            return flagService.getCandidates(flag);
        }

        return null;
    }
    @PatchMapping("/{flagId}/updateState") //확정으로 변경
    @Operation(summary = "flag 상태 변경", description = "플래그의 확정 / 확정 시간 저장")
    public String updateState(@PathVariable("flagId") Long flagId, @RequestBody @Valid String date) {
        try {
            flagService.updateFlagState(flagId, date);
            return "완료";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/{flagId}/checkState") //확정 가능한 상태인지 검사.
    @Operation(summary = "flag 확정 가능여부", description = "모두 응답하여 플래그 확정 가능 여부 확인")
    public boolean checkState(@PathVariable("flagId") Long flagId) {
        try {
            return flagService.checkNonResponse(flagId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{flagId}/fixed") //확정됐는지 아닌지 flag 확인- 호스트
    @Operation(summary = "flag 상태 반환", description = "플래그의 확정 / 진행 상태 반환")
    public boolean checkStateFlag(@PathVariable("flagId") Long flagId) {
        try {
            Flag flag = flagService.getFlagState(flagId);
            boolean state = flag.getState();
            return state;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}/fixedlist") // 확정 list
    @Operation(summary = "flag 확정 list", description = "user의 플래그의 확정 리스트 반환")
    public List<Flag> getFixFlagList(@PathVariable("userId") Long id) {
        try {
            return flagService.getFixedFlagList(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}/progresslist") // 진행 list
    @Operation(summary = "flag 진행 list", description = "user의 플래그의 진행 리스트 반환")
    public List<Flag> getProgressFlagList(@PathVariable("userId") Long id) {
        try {
            return flagService.getProgressFlagList(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/info/{flagId}") //상세 정보
    @Operation(summary = "flag 상세보기", description = "flag정보 반환")
    public Flag getFlagInfo(@PathVariable("flagId") Long id) {
        try {
            return flagService.getFlag(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{flagId}/{cellIndex}")
    @Operation(summary = "flag 셀 정보 보기", description = "flag 셀 선택 시 시간 및 가능한 인원들 반환")
    public FlagCellRes getFlagCell(@PathVariable("flagId") Long id, @PathVariable("cellIndex") int index) {
        return flagService.getFlagCellRes(id, index);
    }
}
