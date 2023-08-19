package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.GuestFlagDto;
import com.flag.flag_back.service.UserFlagManagerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserFlagManagerController {

    private final UserFlagManagerService userFlagManagerService;

    @PostMapping("/flag/{userId}/{flagId}")
    @Operation(summary = "guest가 flag에 정보 입력", description = "guest가 초대된 flag의 수락여부를 결정하고 만약 수락한다면 정보들을 입력합니다.")
    public String addGuestFlag(@PathVariable("userId") Long userId, @PathVariable("flagId") Long flagId, @RequestBody @Valid GuestFlagDto guestFlagDto) {
        userFlagManagerService.addGuestFlag(userId, flagId, guestFlagDto);
        return "redirect:/";
    }
}
