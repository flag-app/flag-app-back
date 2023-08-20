package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.*;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.jwt.JwtTokenProvider;
import com.flag.flag_back.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Api(tags = "User Controller", value = "회원 정보 관리 기능 구현한 User Controller 입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인
    @Operation(summary = "로그인", description = "로그인 API")
    @PostMapping("/login")
    public String login(@RequestBody SignReq signReq) {
        User user = userRepository.findUserByEmail(signReq.getEmail());

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }

        if (!user.getPassword().equals(signReq.getPassword())) {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }

        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/join")
    public UserRes create(@Parameter(description = "회원 ID", required = true, example = "1") @RequestBody @Valid UserInfo request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setProfile(request.getProfile());

        Long id = userService.join(user);
        return new UserRes(id);
    }

    //  @ResponseBody
    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long id) {
        System.out.println(userRepository.findUserEntityByUserId(id));

        try {
            return userService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/nickname")
    @Operation(summary = "닉네임 변경", description = "닉네임 변경 api입니다.")
    public UserRes updateName(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody String newName) {
        try {
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            user.setName(newName);
            userService.save(user); // 새로운 이름으로 업데이트된 사용자 정보 저장
            return new UserRes(user.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/profile")
    @Operation(summary = "프로필 변경", description = "프로필 변경 API입니다.")
    public ResponseDto<String> updateProfile(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody String newProfile) {
        try {
            // 사용자 정보 가져오기
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);
            // 새 프로필 정보로 업데이트
            user.setProfile(newProfile);
            userService.save(user);
            return ResponseDto.success("프로필 변경 성공", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/password/change")
    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 API 입니다. 기존 비밀번호와 새 비밀번호를 요청 값으로 받습니다.")
    public ResponseDto<String> updatePassword(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        try {
            // 사용자 정보 가져오기
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);

            // 기존 비밀번호와 새 비밀번호를 통해 비밀번호 변경 작업 수행
            boolean passwordChanged = userService.changePassword(user, changePasswordRequestDto.getOldPassword(), changePasswordRequestDto.getNewPassword());

            if (passwordChanged) {
                return ResponseDto.success("비밀번호 변경 성공", null);
            } else {
                return ResponseDto.fail(HttpStatus.BAD_REQUEST, "비밀번호 변경 실패", null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{userId}/password2")
    @Operation(summary = "비밀번호 찾기한 후 변경", description = "비밀번호 변경 API 입니다. 새 비밀번호를 요청 값으로 받습니다.")
    public ResponseDto<String> updatePassword(@PathVariable("userId") Long id, @RequestBody @NotBlank String newPassword) {
        try {
            // 사용자 정보 가져오기
            User user = userService.findById(id);

            // 새 비밀번호로 업데이트
            user.setPassword(newPassword);
            userService.save(user);

            return ResponseDto.success("비밀번호 변경 성공", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/email-by-name")
    @Operation(summary = "이메일 찾기", description = "이메일 찾기 API 입니다. 닉네임값을 요청 값으로 받습니다.")
    public String getEmailByName(@RequestParam("name") String name) {
        try {
            // 이름을 이용하여 이메일 찾기
            User user = userRepository.findUserByName(name);

            if (user != null) {
                return user.getEmail();
            } else {
                return "이름에 해당하는 사용자를 찾을 수 없습니다.";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/checkName")
    @Operation(summary = "닉네임 중복 검사", description = "닉네임 중복 검증 API입니다.")
    public String checkExistName(@RequestBody @NotBlank String name) {
        try {
            System.out.println("name - " + name);
            // 이름을 이용하여 이메일 찾기
            User user = userRepository.findUserByName(name);
            //System.out.println("user - " + user.toString());
            if (user != null) {
                return "이미 존재하는 닉네임입니다.";
            } else {
                return "사용가능한 닉네임입니다.";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API입니다.")
    public ResponseDto<String> deleteUser(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            // 사용자 정보 가져오기
            String email = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(email);

            if (user != null) {
                userService.deleteUser(user); // 사용자 정보 삭제

                return ResponseDto.success("회원 탈퇴 성공", null);
            } else {
                return ResponseDto.fail(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.", null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
