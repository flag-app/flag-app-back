package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.UserDto;
import com.flag.flag_back.Dto.UserRes;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final HttpSession httpSession; // 접속했는지 안했는지 확인
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;

    @GetMapping("/login")//GetMapping : "/user/login"으로 매핑된다. 뷰 리졸버를 통해서 "login.html"을 호출한다.
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginId(@ModelAttribute UserDto userDto) {//PostMapping: "/user//login"으로 매핑된다. LoginService의 login 메소드를 실행한다.
        userService.login(userDto);
        return "redirect:/";

    }

    @PostMapping("/join")
    public UserRes create(@RequestBody UserDto request) {  //@Valid이거 왜 문제 생기는지 모르겟음..

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        Long id = userService.join(user);
        return new UserRes(id);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        httpSession.removeAttribute("email");
        if (httpSession != null) {
            // 현재 사용하고 있는 세션 무효화
            httpSession.invalidate();
        }
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/{Id}")
    public UserDto getUser(@RequestParam(value = "id", required = false)Long id) {
        try {
            return userService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
