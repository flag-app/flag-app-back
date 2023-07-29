package com.flag.flag_back.Controller;

import com.flag.flag_back.Dto.FriendDto;
import com.flag.flag_back.Dto.FriendRes;
import com.flag.flag_back.Model.Friend;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.service.FriendService;
import com.flag.flag_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("friends")
public class FriendController {
    @Autowired
    private final FriendService friendService;

    @Autowired
    private final UserService userService;

    @GetMapping("/List/{name}") //닉네임으로 리스트 조회
    public List<User> getUsersList(@PathVariable("name") String name) {
        try {
            return userService.findListByName(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public FriendRes create(@RequestBody @Valid FriendDto dto) {

        Friend friend = new Friend();
        friend.setUserId1(dto.getUserId1());
        friend.setUserId2(dto.getUserId2());

        Long id = friendService.add(friend);
        return new FriendRes(id);
    }
}
