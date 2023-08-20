package com.flag.flag_back.service;

import com.flag.flag_back.Dto.GuestFlagDto;
import com.flag.flag_back.Model.*;
import com.flag.flag_back.Repository.FlagRepository;
import com.flag.flag_back.Repository.UserFlagManagerRepository;
import com.flag.flag_back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserFlagManagerService {

    private final UserRepository userRepository;
    private final FlagRepository flagRepository;

    @Transactional
    public void addGuestFlag(Long userId, Long flagId, GuestFlagDto guestFlagDto) {
        Flag flag = flagRepository.findById(flagId).orElse(null);
        if (flag == null)
            throw new IllegalStateException("존재하지 않는 플래그입니다.");
        UserFlagManager guestFlagManager = findUserFlagManager(userId, flagId);
        guestFlagManager.acceptFlag();
        Day day = new Day(guestFlagManager, new ArrayList<>(flag.getDates()));
        day.setSchedule(guestFlagDto.getPossibleDates());
        guestFlagManager.setDay(day);
        flag.checkState();
    }

    @Transactional
    public UserFlagManager findUserFlagManager(Long userId, Long flagId) {
        User user = userRepository.findUserEntityByUserId(userId);
        List<UserFlagManager> userFlagManagers = user.getUserFlagManagers();
        for (UserFlagManager userFlagManager : userFlagManagers) {
            if (userFlagManager.getFlag().getId().equals(flagId))
                return userFlagManager;
        }
        return null;
    }
}
