package com.flag.flag_back.service;


import com.flag.flag_back.Dto.UserResponse;
import com.flag.flag_back.Model.Friend;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.FriendJpaRepository;
import com.flag.flag_back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FriendService {
    private FriendJpaRepository friendJpaRepository;
    private UserRepository userRepository;
    @Transactional
    public Long add(Friend friend) {
        findMem(friend);
        friendJpaRepository.save(friend);
        return friend.getId();
    }

    private void findMem(Friend friend) {
        User me = userRepository.findUserEntityByUserId(friend.getUserId());
        User per = userRepository.findUserEntityByUserId(friend.getUserId2());

        System.out.println("myname - " + me.getName() + ", yourname - " + per.getName());
    }
//    @Transactional
//    public List<Friend> findListById(Long id) {
//        //User us = userRepository.findUserEntityById(id);
//        List<Friend> f = friendJpaRepository.findByUserId(id);
//        return f;
//    }
    @Transactional
    public List<UserResponse> friendsListById(Long id) {
        List<User> users = userRepository.findFriendListByUserId(id);

        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getUserId());
            userResponse.setEmail(user.getEmail());
            userResponse.setName(user.getName());
            // 나머지 필드들도 복사
            userResponseList.add(userResponse);
        }
        return userResponseList;
    }

    @Transactional
    public UserResponse friendsListByNickName(Long id, String name) {
        //User us = userRepository.findUserEntityById(id);
        User user = userRepository.findFriendListByName(id, name);
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getUserId());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());

        return userResponse;
    }

//    @Transactional
//    public UserResponse friendsListByEmail(String name) {
//        User user = userRepository.findFriendListByName(id, name);
//        UserResponse userResponse = new ArrayList<>();
//
//        userResponse.setId(user.getUserId());
//        userResponse.setEmail(user.getEmail());
//        userResponse.setName(user.getName());
//
//        return userResponse;
//    }


    @Transactional
    public boolean checkFriendById2(Long id, Long fid) {
        Friend friend = friendJpaRepository.findUserEntityByUserIdAndUserId2(id, fid);
        if(friend == null) {
            return false;
        }
        return true;
    }
//
    @Transactional
    public Integer delete(Long id, Long fid) {
        Friend friend = friendJpaRepository.findUserEntityByUserIdAndUserId2(id, fid);
        if(friend != null) {
            friendJpaRepository.delete(friend);
            return 1;
        }return 0;
    }
}
