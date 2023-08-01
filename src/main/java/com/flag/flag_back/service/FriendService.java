package com.flag.flag_back.service;


import com.flag.flag_back.Model.Friend;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.FriendJpaRepository;
import com.flag.flag_back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<User> friendsListById(Long id) {
        //User us = userRepository.findUserEntityById(id);
        List<User> users = userRepository.findFriendListByUserId(id);
        return users;
    }

    @Transactional
    public List<User> friendsListByNickName(Long id, String name) {
        //User us = userRepository.findUserEntityById(id);
        List<User> users = userRepository.findFriendListByName(id, name);
        return users;
    }
}
