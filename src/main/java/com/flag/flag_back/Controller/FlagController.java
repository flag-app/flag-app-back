package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.*;
import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.config.BaseResponse;
import com.flag.flag_back.jwt.JwtTokenProvider;
import com.flag.flag_back.service.FlagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.flag.flag_back.config.BaseResponseStatus.*;
import static com.flag.flag_back.config.BaseResponseStatus.FLAG_DELETE_FAIL;

@RestController
@RequiredArgsConstructor
@RequestMapping("flag")
public class FlagController {

    private final FlagService flagService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    @Operation(summary = "flag 생성", description = "flag 생성합니다.")
    public BaseResponse<String> createFlag(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody @Valid FlagDto flagDto) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            flagService.createFlag(flagDto, user);
            return new BaseResponse<>(FLAG_CREATE_SUCCESS);
        } catch (Exception e) {
            return new BaseResponse<>(FLAG_CREATE_FAIL);
        }
    }

    @DeleteMapping("/{flagId}")
    @Operation(summary = "flag 삭제", description = "flag를 삭제합니다.")
    public BaseResponse<String> deleteFlag(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("flagId") Long flagId) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            userRepository.findUserByEmail(email);
            flagService.deleteFlag(flagId);
            return new BaseResponse<>(FLAG_DELETE_SUCCESS);
        } catch (Exception e) {
            return new BaseResponse<>(FLAG_DELETE_FAIL);
        }
    }

    @PatchMapping("/{flagId}")
    @Operation(summary = "flag 수정", description = "flag 정보(이름,장소,메모)를 수정합니다.")
    public BaseResponse<String> updateFlag(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("flagId") Long flagId, @RequestBody @Valid FlagDto flagDto) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            userRepository.findUserByEmail(email);
            System.out.print(flagDto);
            flagService.updateFlag(flagId, flagDto);
            return new BaseResponse<>(FLAG_UPDARE_SUCCESS);
        } catch (Exception e) {
            return new BaseResponse<>(FLAG_UPDARE_FAIL);
        }
    }

    @GetMapping("/{flagId}/show")
    @Operation(summary = "flag 정보 조회", description = "총 인원 수, 되는 인원, 무응답 인원, 가능한 인원의 셀들을 반환합니다.")
    public FlagTimeTableRes getFlagTimeTable(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("flagId") Long flagId) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            return flagService.getFlagTimeTableRes(user.getUserId(), flagId);
        } catch (Exception e) {
            e.printStackTrace();

            return FlagTimeTableRes.builder().timeSlot(-1).userTotalCount(-1).dates(Collections.singletonList("flag 정보 조회에 실패했습니다.")).acceptUsers(Collections.singletonList("")).nonResponseUsers(Collections.singletonList("")).ableCells(Collections.singletonList(-1)).build();
        }
    }


    @GetMapping("/{flagId}/candidate")
    @Operation(summary = "flag 후보 조회", description = "최소 시간을 만족하는 flag 후보를 반환합니다.")
    public List<CandidateRes> getCandidates(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("flagId") Long flagId) {
        try {
            if (token == null || !jwtTokenProvider.validateToken(token)) {
                return Collections.singletonList(new CandidateRes("유효하지 않은 토큰", "", "", -1, Collections.emptyList()));
            }

            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            return flagService.getCandidates(user.getUserId(), flagId);
        } catch (Exception e) {
            return Collections.singletonList(new CandidateRes("flag 후보 조회 실패", "", "", -1, Collections.emptyList()));
        }
    }

    /*@PatchMapping("/{flagId}/updateState") //확정으로 변경
    @Operation(summary = "flag 상태 변경", description = "플래그의 확정 / 확정 시간 저장")
    public String updateState(@PathVariable("flagId") Long flagId, @RequestBody @Valid String date) {
        try {
            flagService.updateFlagState(flagId, date);
            return "완료";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/


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

    @GetMapping("/fixedlist") // 확정 list
    @Operation(summary = "flag 확정 list", description = "user의 플래그의 확정 리스트 반환")
    public List<FixedFlagRes> getFixFlagList(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            FixedFlagRes errorFlag = new FixedFlagRes(null, "Error", null, null, null, "올바른 Authorization인지 확인하세요.", "플래그의 확정 리스트 반환에 실패했습니다", null, null);
            return Collections.singletonList(errorFlag);
        }

        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);

            return flagService.getFixedFlagList(user.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/progresslist") // 진행 list
    @Operation(summary = "flag 진행 list", description = "user의 플래그의 진행 리스트 반환")
    public List<ProgressFlagRes> getProgressFlagList(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            ProgressFlagRes errorFlag = new ProgressFlagRes(null, "플래그의 진행 리스트 반환에 실패했습니다.", "올바른 Authorization인지 확인하세요.", null, 0);
            return Collections.singletonList(errorFlag);
        }
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            return flagService.getProgressFlagList(user.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/info/{flagId}") //상세 정보
    @Operation(summary = "flag 상세보기", description = "flag정보 반환")
    public Flag getFlagInfo(@PathVariable("flagId") Long id) {
        try {
            if(flagService.getFlag(id) == null) {
                Flag errorFlag = new Flag("flag 상세보기에 실패했습니다.", -1, null, null, null, null); // 예외 메시지를 담은 Flag 객체 생성
                return errorFlag;
            }

            return flagService.getFlag(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{flagId}/{cellIndex}")
    @Operation(summary = "flag 셀 정보 보기", description = "flag 셀 선택 시 시간 및 가능한 인원들 반환")
    public FlagCellRes getFlagCell(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("flagId") Long flagId, @PathVariable("cellIndex") int index) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            return flagService.getFlagCellRes(user.getUserId(), flagId, index);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            String errorMessage = "flag 셀 정보를 가져오는 동안 오류가 발생했습니다: " + e.getMessage();
            return new FlagCellRes("flag 셀 선택 오류", "", "", Collections.singletonList(errorMessage)); // 예외 메시지를 담은 FlagCellRes 객체 생성
        }
    }

    @PostMapping("/{flagId}/candidate/fix")
    @Operation(summary = "flag 확정 짓기", description = "flag 후보들 중 확정할 후보의 인덱스를 받습니다.")
    public String fixFlag(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("flagId") Long flagId, @RequestBody @Valid int index) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            flagService.fixFlag(user.getUserId(), flagId, index);
            return "redirect:/";
        } catch (Exception e) {
            return "플래그 확정 중 오류가 발생했습니다.";
        }
    }
}

