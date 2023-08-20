package com.flag.flag_back.service;

import com.flag.flag_back.Dto.*;
import com.flag.flag_back.Model.*;
import com.flag.flag_back.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@AllArgsConstructor
@Service
public class FlagService {

    private static final String ON_TIME = ":00";
    private static final String ON_HALF = ":30";

    private final UserRepository userRepository;
    private final FlagRepository flagRepository;
    private final UserFlagManagerService userFlagManagerService;

    @Transactional
    public Long createFlag(FlagDto flagDto, User host) {
        Flag flag = new Flag(flagDto.getName(), flagDto.getTimeSlot(), flagDto.getMinTime(), flagDto.getPlace(), flagDto.getMemo(), flagDto.getDates());

        // 호스트의 정보 설정
        UserFlagManager hostFlagManager = new UserFlagManager(flag, host, FlagRole.HOST, FlagStatus.ACCEPT);
        Day day = new Day(hostFlagManager, flagDto.getDates());
        day.setSchedule(flagDto.getPossibleDates());
        hostFlagManager.setDay(day);
        flag.addUserFlagManager(hostFlagManager);

        // 게스트의 정보 설정
        for (String guestName : flagDto.getGuestNames()) {
            User guest = userRepository.findUserByName(guestName);
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
    public String updateFlagState(Long flagId, String date) {
        Flag flag = flagRepository.findById(flagId).orElse(null);
        if (flag != null) {
            // Update the flag information based on the flagDto
            String dates = date;
            System.out.println("플래그 date?!" + dates);
            try {
                SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                Date fixdate = isoFormat.parse(date);

                flag.setState(true);
                flag.setFixedDate(fixdate);

                flagRepository.save(flag);
                // 서비스 호출 및 로직 처리
                return "완료";
            } catch (ParseException e) {
                throw new RuntimeException("Invalid date format", e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Flag not found with id: " + flagId);
        }
    }

    @Transactional
    public Flag getFlag(Long flagId) {
        return flagRepository.findById(flagId).orElse(null);
    }

    @Transactional
    public FlagCellRes getFlagCellRes(Long flagId, int index) {
        Flag flag = flagRepository.findById(flagId).orElse(null);
        if (flag == null)
            throw new IllegalStateException();
        int cnt = flag.getDates().size();
        int val = index / cnt - 1;
        return new FlagCellRes(flag.getDates().get(index % cnt),
                convertIndexToTime(flag.getTimeSlot(), val),
                convertIndexToTime(flag.getTimeSlot(), val + 1),
                setAvailableMember(getAvailableMember(flag, index)));
    }

    @Transactional
    public FlagTimeTableRes getFlagTimeTableRes(Long userId, Long flagId) {
        Flag flag = flagRepository.findById(flagId).orElse(null);
        if (flag == null)
            throw new IllegalStateException();
        UserFlagManager userFlagManager = userFlagManagerService.findUserFlagManager(userId, flagId);
        if (userFlagManager == null)
            throw new IllegalStateException();
        return new FlagTimeTableRes(flag.getUserCount(), flag.getAcceptUsers(),
                flag.getNonResponseUsers(), flag.getCellIndexes());
    }

    public List<CandidateRes> getCandidates(Long userId, Long flagId) {
        Flag flag = flagRepository.findById(flagId).orElse(null);
        if (flag == null)
            throw new IllegalStateException();
        UserFlagManager userFlagManager = userFlagManagerService.findUserFlagManager(userId, flagId);
        if (userFlagManager == null)
            throw new IllegalStateException();

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
                    List<String> members = setAvailableMember(new ArrayList<>(init));
                    ret.add(new CandidateRes(flag.getDates().get(i),
                            convertIndexToTime(flag.getTimeSlot(), startIndex / standardIndex - 1),
                            convertIndexToTime(flag.getTimeSlot(), currentIndex / standardIndex - 1),
                            currentIndex - startIndex,
                            members));
                }

                startIndex = currentIndex;
            }
        }

        ret.sort(new CandidateResComparator());
        return ret;
    }

    private List<String> setAvailableMember(List<Long> temp) {
        List<String> ret = new ArrayList<>();
        for (Long id : temp) {
            ret.add(userRepository.findUserEntityByUserId(id).getName());
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

    private String convertIndexToTime(int timeSlot, int index) {
        String suffix = index % 2 == 1 ? ON_HALF : ON_TIME;
        int time = timeSlot + (index / 2);
        return time < 10 ? "0" + time + suffix : time + suffix;
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

    public boolean checkNonResponse(Long id) {
        Optional<Flag> flag = flagRepository.findById(id);

        if (flag != null) {
            System.out.println("resX" + flag.get().getNonResponseUsers().toString());
            System.out.println("res0" + flag.get().getAcceptUsers().toString());
           if(flag.get().getNonResponseUsers().isEmpty()) {
               return true;
           }
           return false;
        }

        return false;
    }
}
