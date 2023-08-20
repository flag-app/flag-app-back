package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.GuestFlagDto;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.jwt.JwtTokenProvider;
import com.flag.flag_back.service.UserFlagManagerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserFlagManagerController {

    private final UserFlagManagerService userFlagManagerService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/flag/guest/{flagId}")
    @Operation(summary = "guest가 flag에 정보 입력", description = "guest가 초대된 flag의 수락여부를 결정하고 만약 수락한다면 정보들을 입력합니다.")
    public String addGuestFlag(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable("flagId") Long flagId, @RequestBody @Valid GuestFlagDto guestFlagDto) {
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findUserByEmail(email);
        userFlagManagerService.addGuestFlag(user.getUserId(), flagId, guestFlagDto);
        return "redirect:/";
    }
}
