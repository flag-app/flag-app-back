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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        friend.setUserId(dto.getUserId());
        friend.setUserId2(dto.getUserId2());

        Long id = friendService.add(friend);
        return new FriendRes(id);
    }

    //친구 리스트 보여줌.
    @GetMapping("/friendList/{id}")
    public List<User> getFriendsList(@PathVariable("id") Long id) {
        try {
            return friendService.friendsListById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //내 친구 내에서 검색 - select문을 - where userid = user2Id where userid1 =  myId
    @GetMapping("/friendList/{id}/{name}") //닉네임으로 리스트 조회
    public List<User> searchFriendsList(@PathVariable("id") Long id, @PathVariable("name") String name) {
        try {
            return friendService.friendsListByNickName(id, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //친구 삭제
    @DeleteMapping("/delete/{id}/{fid}")
    public Map<String, Object> delete(@PathVariable("id") Long id, @PathVariable("fid") Long fid) {
        Map<String, Object> response = new HashMap<>();

        if(friendService.delete(id,fid) > 0) {
            response.put("result", "SUCCESS");
        } else {
            response.put("result", "FAIL");
            response.put("reason", "일치하는 정보가 없습니다. id를 확인해주세요.");
        }

        return response;
    }
}
