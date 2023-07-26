package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.UserDto;
import com.flag.flag_back.Dto.UserInfo;
import com.flag.flag_back.Dto.UserRes;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.UserRepository;
import com.flag.flag_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

    @PostMapping("/join")
    public UserRes create(@RequestBody @Valid UserInfo request) {

        System.out.println("여기까지 들어옴");
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

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
    public UserInfo getUser(@PathVariable("userId") Long id) {
        System.out.println("id 1: " + id);
        System.out.println(userRepository.findUserById(id));

        try {
            return userService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
