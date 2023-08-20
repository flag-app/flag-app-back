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

@Api(tags = "User Controller", value = "로그인 로그아웃 회원가입 기능 구현한 User Controller 입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final HttpSession httpSession; // 접속했는지 안했는지 확인
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login") //GetMapping : "/user/login"으로 매핑된다. 뷰 리졸버를 통해서 "login.html"을 호출한다.
    public String login(Model model) {
        model.addAttribute("data", "hello!!!");
        return "login";
    }

    // 로그인
    @Operation(summary = "로그인", description = "로그인 API")
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> member) {
        log.info("user email = {}", member.get("email"));
        User user = userRepository.findUserByEmail(member.get("email"));

        if (!user.getEmail().equals(member.get("email"))) {
            System.out.println("로그인 실패???");
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }

        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }

    @GetMapping("/join")
    public String create(Model model) {
        model.addAttribute("userInfo", new UserInfo());
        return "createUser";
    }

    @PostMapping("/join")
    public UserRes create(@Parameter(description = "회원 ID", required = true, example = "1") @RequestBody @Valid UserInfo request) {

        System.out.println("여기까지 들어옴");
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setProfile(request.getProfile());

        System.out.println("여까지도 성공~");

        Long id = userService.join(user);
        return new UserRes(id);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        //System.out.println("User ID: " + userDto.getEmail());//세션 유지되는지 검증하는 코드
        httpSession.removeAttribute("user");
        if (httpSession != null) {
            // 현재 사용하고 있는 세션 무효화
            httpSession.invalidate();
        }
        return "redirect:/";
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

    @PatchMapping("/{userId}/nickname")
    @Operation(summary = "닉네임 변경", description = "닉네임 변경 api입니다.")
    public UserRes updateName(@PathVariable("userId") Long id, @RequestBody String newName) {

        try {
            User user = userService.findById(id);
            user.setName(newName);
            userService.save(user); // 새로운 이름으로 업데이트된 사용자 정보 저장

            return new UserRes(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{userId}/profile")
    @Operation(summary = "프로필 변경", description = "프로필 변경 API입니다.")
    public ResponseDto<String> updateProfile(@PathVariable("userId") Long id, @RequestBody String newProfile) {
        try {
            // 사용자 정보 가져오기
            User user = userService.findById(id);

            // 새 프로필 정보로 업데이트
            user.setProfile(newProfile);
            userService.save(user);

            return ResponseDto.success("프로필 변경 성공", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{userId}/password1")
    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 API 입니다. 기존 비밀번호와 새 비밀번호를 요청 값으로 받습니다.")
    public ResponseDto<String> updatePassword(@PathVariable("userId") Long id, @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        try {
            // 사용자 정보 가져오기
            User user = userService.findById(id);

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

    @DeleteMapping("/{userId}")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API입니다.")
    public ResponseDto<String> deleteUser(@PathVariable("userId") Long id) {
        try {
            // 사용자 정보 가져오기
            User user = userService.findById(id);

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
