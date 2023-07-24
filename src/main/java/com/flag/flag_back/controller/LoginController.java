//package com.flag.flag_back.Controller;
//
//import com.flag.flag_back.Dto.UserDto;
//import com.flag.flag_back.Repository.UserRepository;
//import com.flag.flag_back.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//
//@RequiredArgsConstructor
//@Controller
//public class LoginController {
//    @Autowired
//    private final UserRepository userRepository;
//    @Autowired
//    private final UserService userService;
//
//    @GetMapping("/user/login")//GetMapping : "/user/login"으로 매핑된다. 뷰 리졸버를 통해서 "login.html"을 호출한다.
//    public String login() {
//        return "login";
//    }
//
//    @PostMapping("/user/login")
//    public String loginId(@ModelAttribute UserDto userDto) {//PostMapping: "/user//login"으로 매핑된다. LoginService의 login 메소드를 실행한다.
//
//        userService.login(userDto);
//
//        return "redirect:/";
//
//    }
//}
