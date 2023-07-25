package com.flag.flag_back.service;


import com.flag.flag_back.Repository.FriendJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FriendService {
    private FriendJpaRepository friendJpaRepository;

}
