package com.flag.flag_back.service;

import com.flag.flag_back.Dto.FlagDto;
import com.flag.flag_back.Model.*;
import com.flag.flag_back.Repository.DayRepository;
import com.flag.flag_back.Repository.FlagMemberRepository;
import com.flag.flag_back.Repository.FlagRepository;
import com.flag.flag_back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FlagService {

    private final UserRepository userRepository;
    private final FlagRepository flagRepository;
    private final FriendService friendService;
    private final DayRepository dayRepository;
    private final FlagMemberRepository flagMemberRepository;

    @Transactional
    public Long createFlag(FlagDto flagDto) {
        Flag flag = new Flag(flagDto.getName(), flagDto.getMinTime(), flagDto.getPlace(), flagDto.getMemo(), false, flagDto.getDates());

        // 호스트의 정보 설정
        User host = userRepository.findUserEntityByUserId(flagDto.getHostId());
        UserFlagManager hostFlagManager = new UserFlagManager(flag, host, FlagRole.HOST, FlagStatus.ACCEPT);
        Day day = new Day(hostFlagManager, flagDto.getDates());
        day.setSchedule(flagDto.getPossibleDates());
        hostFlagManager.setDay(day);
        flag.addUserFlagManager(hostFlagManager);

        // 게스트의 정보 설정
        for (Long id : flagDto.getGuestId()) {
            User guest = userRepository.findUserEntityByUserId(id);
            UserFlagManager guestFlagManager = new UserFlagManager(flag, guest, FlagRole.GUEST, FlagStatus.STANDBY);
            guestFlagManager.setDay(new Day(guestFlagManager, flagDto.getDates()));
            flag.addUserFlagManager(guestFlagManager);
        }

        flagRepository.save(flag);
        return flag.getId();
    }

    @Transactional
    public Flag getFlag(Long flagId) {
        Optional<Flag> flag = flagRepository.findById(flagId);
        if (flag != null) {
            return flag.get();
        }
        return null;
    }

    public List<List<Long>> getCandidates(Flag flag) {
        List<List<Long>> ret = new ArrayList<>();
        int standardIndex = flag.getDates().size();

        // 날짜별로 보기 위해
        for (int i = 0; i <  standardIndex; i++) {

            // 한 날짜의 모든 시간대를 탐색
            for (int startIndex = standardIndex + i; startIndex <  standardIndex * 13;) {
                HashSet<Long> init = new HashSet<>(getAvailableMember(flag, startIndex));
                int cnt = 1;
                int currentIndex = startIndex + standardIndex;
                int standardSize = init.size();

                while(currentIndex <  standardIndex * 13) {
                    List<Long> candidate = getAvailableMember(flag, currentIndex);
                    init.addAll(candidate);

                    if (init.size() != standardSize) {
                        break;
                    }

                    cnt++;
                    currentIndex += standardIndex;
                }

                // 최소 시간을 충족한다면 후보로 등록
                if (cnt >= flag.getMinTime() * 2) {
                    ret.add(new ArrayList<>(init));
                }
            }
        }

        return ret;
    }

    public List<Long> getAvailableMember(Flag flag, int index) {
        List<Long> ret = new ArrayList<>();
        for (UserFlagManager userFlagManager : flag.getUserFlagManagers()) {
            if (userFlagManager.getStatus() != FlagStatus.ACCEPT) {
                continue;
            }
            if (userFlagManager.ableOrNot(index)) {
                ret.add(userFlagManager.getUser().getUserId());
            }
        }
        return ret;
    }

    // 가능한 셀 번호 목록을 모두 반환 (ios 요청)
    public List<Integer> getCellIndexes(Flag flag) {
        List<Integer> ret = new ArrayList<>();
        for (UserFlagManager userFlagManager : flag.getUserFlagManagers()) {
            if (userFlagManager.getStatus() != FlagStatus.ACCEPT) {
                continue;
            }
            ret.addAll(addAbleCellIndex(userFlagManager));
        }
        return ret;
    }

    public List<Integer> addAbleCellIndex(UserFlagManager userFlagManager) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (userFlagManager.ableOrNot(i)) {
                ret.add(i);
            }
        }
        return ret;
    }

    public int getUserCount(Flag flag) {
        return flag.getUserFlagManagers().size();
    }
}
