package com.flag.flag_back.Controller;

import com.flag.flag_back.Model.User;
import com.flag.flag_back.service.FriendService;
import com.flag.flag_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("friends")
public class FriendController {
    @Autowired
    private final FriendService friendService;

    @Autowired
    private final UserService userService;

    @GetMapping("/List/{name}")
    public List<User> getUsersList(@PathVariable("name") String name) {
        try {
            return userService.findListByName(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
