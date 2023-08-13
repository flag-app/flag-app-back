package com.flag.flag_back.service;

import com.flag.flag_back.Model.Flag;
import com.flag.flag_back.Repository.DayRepository;
import com.flag.flag_back.Repository.FlagMemberRepository;
import com.flag.flag_back.Repository.FlagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class FlagService {
    private final FlagRepository flagRepository;
    private final FriendService friendService;
    private final DayRepository dayRepository;
    private final FlagMemberRepository flagMemberRepository;
    @Transactional
    public Long createFlag(Flag flag) {
        System.out.println("service 1 ---" + flag.toString());
//        dayRepository.save(flag.getDayList().get(0));
//        flagMemberRepository.save(flag.getFriendsList().get(0));
        flagRepository.save(flag);

        System.out.println("service 2 ---" + flag.toString());
        System.out.println("id-sv : " + flag.getId());
        return flag.getId();
    }
}
