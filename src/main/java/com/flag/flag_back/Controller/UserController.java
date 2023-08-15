package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.*;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Tag(name = "User Controller", description = "로그인 로그아웃 회원가입 기능 구현한 User Controller 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final HttpSession httpSession; // 접속했는지 안했는지 확인
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;

    @GetMapping("/login") //GetMapping : "/user/login"으로 매핑된다. 뷰 리졸버를 통해서 "login.html"을 호출한다.
    public String login(Model model) {
        model.addAttribute("data", "hello!!!");
        return "login";
    }

    @Operation(summary = "로그인", description = "로그인 기능입니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/login")
    public String loginId(@RequestBody @Valid UserDto userDto, HttpSession session, Model model) throws Exception {//PostMapping: "/user//login"으로 매핑된다. LoginService의 login 메소드를 실행한다.
        userService.login(userDto);

        if (userDto != null) {
            session.setAttribute("user", userDto);
            System.out.print(userDto);
            return "redirect:/";
        }
        model.addAttribute("isLoginSuccess", false);
        return "redirect:login";
    }

    @GetMapping("/join")
    public String create(Model model) {
        model.addAttribute("userInfo", new UserInfo());
        return "createUser";
    }

    @Operation(summary = "회원 가입", description = "회원 가입 기능입니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
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

    @PatchMapping("/nickname")
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
}
