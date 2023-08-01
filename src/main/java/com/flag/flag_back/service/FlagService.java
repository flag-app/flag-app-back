package com.flag.flag_back.service;

import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.Repository.FlagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class FlagService {
    private final FlagRepository flagRepository;
    private final FriendService friendService;

    @Transactional
    public Long createFlag(Flag flag) {
        flagRepository.save(flag);
        return flag.getId();
    }
}
