package com.flag.flag_back.service;

import com.flag.flag_back.Dto.CandidateRes;
import com.flag.flag_back.Dto.FlagDto;
import com.flag.flag_back.Dto.FlagTimeTableRes;
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
        System.out.println(host.getName());
        UserFlagManager hostFlagManager = new UserFlagManager(flag, host, FlagRole.HOST, FlagStatus.ACCEPT);
        Day day = new Day(hostFlagManager, flagDto.getDates());
        day.setSchedule(flagDto.getPossibleDates());
        hostFlagManager.setDay(day);
        flag.addUserFlagManager(hostFlagManager);

        // 게스트의 정보 설정
        for (Long id : flagDto.getGuestId()) {
            User guest = userRepository.findUserEntityByUserId(id);
            System.out.println(guest.getName());
            UserFlagManager guestFlagManager = new UserFlagManager(flag, guest, FlagRole.GUEST, FlagStatus.STANDBY);
            guestFlagManager.setDay(new Day(guestFlagManager, flagDto.getDates()));
            flag.addUserFlagManager(guestFlagManager);
        }

        flagRepository.save(flag);
        return flag.getId();
    }

    @Transactional
    public void deleteFlag(Long flagId) {
        Flag flag = flagRepository.findById(flagId).orElse(null);
        if (flag != null) {
            flagRepository.delete(flag);
        } else {
            throw new RuntimeException("Flag not found with id: " + flagId);
        }
    }

    @Transactional
    public void updateFlag(Long flagId, FlagDto flagDto) {
        Flag flag = flagRepository.findById(flagId).orElse(null);
        if (flag != null) {
            // Update the flag information based on the flagDto
            flag.setName(flagDto.getName());
            flag.setPlace(flagDto.getPlace());
            flag.setMemo(flagDto.getMemo());

            // Save the updated flag to the database
            flagRepository.save(flag);
        } else {
            throw new RuntimeException("Flag not found with id: " + flagId);
        }
    }

    @Transactional
    public Flag getFlag(Long flagId) {
        System.out.println("플래그 아이디는?!" + flagId);
        Optional<Flag> flag = flagRepository.findById(flagId);
        System.out.println("플래그 정보는?!" + flag.get().getId());
        if (flag != null) {
            return flag.get();
        }
        return null;
    }

    @Transactional
    public FlagTimeTableRes getFlagTimeTableRes(Long flagId) {
        Optional<Flag> flag = flagRepository.findById(flagId);

        if (flag != null) {
            return new FlagTimeTableRes(flag.get().getUserCount(), flag.get().getAcceptUsers(),
                    flag.get().getNonResponseUsers(), flag.get().getCellIndexes());
        }

        return null;
    }

    public List<CandidateRes> getCandidates(Flag flag) {
        List<CandidateRes> ret = new ArrayList<>();
        int standardIndex = flag.getDates().size();

        // 날짜별로 보기 위해
        for (int i = 0; i < standardIndex; i++) {

            // 한 날짜의 모든 시간대를 탐색
            for (int startIndex = standardIndex + i; startIndex <  standardIndex * 13;) {
                HashSet<Long> init = new HashSet<>(getAvailableMember(flag, startIndex));

                if (init.isEmpty())
                {
                    startIndex += standardIndex;
                    continue;
                }

                int cnt = 1;
                int currentIndex = startIndex + standardIndex;
                int standardSize = init.size();

                while(currentIndex <  standardIndex * 13) {
                    List<Long> candidate = getAvailableMember(flag, currentIndex);

                    // 1차로 인원 수 비교
                    if (candidate.size() != init.size())
                        break;

                    init.addAll(candidate);

                    // 2차로 같은 인원만 속해있었는지 비교
                    if (init.size() != standardSize)
                        break;

                    cnt++;
                    currentIndex += standardIndex;
                }

                // 최소 시간을 충족한다면 후보로 등록
                if (cnt >= flag.getMinTime() * 2) {
                    ret.add(new CandidateRes(flag.getDates().get(i), startIndex, currentIndex - standardIndex, new ArrayList<>(init)));
                }

                startIndex = currentIndex;
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

    public Flag getFlagState(Long id) {
        Optional<Flag> flag = flagRepository.findById(id);
        //System.out.println("flag state - " + flag.toString());
        if (flag != null) {
            return flag.get();
        }
        return null;
    }

    public List<Flag> getFixedFlagList(Long id) {
        List<Flag> flags = flagRepository.findFlagByState(id);
        //System.out.println("flag list - " + flags.toString());
        if (flags.isEmpty()) {
            System.out.println("null");
            return null;
        }
        return flags;
    }

    public List<Flag> getProgressFlagList(Long id) {
        List<Flag> flags2 = flagRepository.findFlagByState2(id);
        //System.out.println("flag list - " + flags.toString());
        if (flags2.isEmpty()) {
            System.out.println("null");
            return null;
        }
        return flags2;
    }
}
