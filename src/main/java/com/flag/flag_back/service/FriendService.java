package com.flag.flag_back.service;


import com.flag.flag_back.Model.Friend;
import com.flag.flag_back.Model.User;
import com.flag.flag_back.Repository.FriendJpaRepository;
import com.flag.flag_back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        User me = userRepository.findUserEntityById(friend.getUserId1());
        User per = userRepository.findUserEntityById(friend.getUserId2());

        System.out.println("myname - " + me.getName() + ", yourname - " + per.getName());
    }
}
