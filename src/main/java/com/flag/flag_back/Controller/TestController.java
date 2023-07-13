package com.flag.flag_back.Controller;

import com.flag.flag_back.Repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final UserJpaRepository userJpaRepository;
    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }
    @GetMapping("/test/db")
    public String testDB() {
        return userJpaRepository.findById(1L).get().getName();
    }//숫자 1에 long타입
}