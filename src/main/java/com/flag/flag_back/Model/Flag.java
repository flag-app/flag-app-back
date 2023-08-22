package com.flag.flag_back.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Data
@Table(name = "FlagTB")
@Entity
@Getter
@Setter
@ToString(exclude = "dates")
public class Flag {
    @Id
    @Column(name = "flagId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flagName")
    private String name;
    @Column(name = "flagTimeSlot")
    private Integer timeSlot;
    @Column(name = "min")
    private Integer minTime;
    @Column(name = "place")
    private String place;
    @Column(name = "memo")
    private String memo;
    @Column(name = "state") // 확정 가능 여부 (모든 사람들이 자신의 일정을 입력했을 때 true로 바뀜)
    private boolean state;
    private LocalDate fixedDate;
    private String startTime;
    private String endTime;

    @ElementCollection
    private List<String> fixedMembers;

    @ElementCollection
    private List<String> dates;

    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL)
    private List<UserFlagManager> userFlagManagers = new ArrayList<>();

    @Builder
    public Flag(String name, Integer timeSlot, Integer minTime, String place, String memo, List<String> dates) {
        this.name = name;
        this.timeSlot = timeSlot;
        this.minTime = minTime;
        this.place = place;
        this.memo = memo;
        this.dates = dates;
        this.state = false;
        this.fixedDate = null;
    }

    public boolean getState() {
        return this.state;
    }

    public void addUserFlagManager(UserFlagManager userFlagManager) {
        this.userFlagManagers.add(userFlagManager);
    }

    public int getUserCount() {
        return userFlagManagers.size();
    }

    public List<String> getAcceptUsers() {
        List<String> ret = new ArrayList<>();
        for (UserFlagManager userFlagManager : userFlagManagers) {
            if (userFlagManager.getStatus() == FlagStatus.ACCEPT) {
                ret.add(userFlagManager.getUser().getName());
            }
        }
        return ret;
    }

    public List<String> getNonResponseUsers() {
        List<String> ret = new ArrayList<>();
        for (UserFlagManager userFlagManager : userFlagManagers) {
            if (userFlagManager.getStatus() != FlagStatus.ACCEPT) {
                ret.add(userFlagManager.getUser().getName());
            }
        }
        return ret;
    }

    // 가능한 셀 번호 목록을 모두 반환 (ios 요청)
    public List<Integer> getCellIndexes() {
        List<Integer> ret = new ArrayList<>();
        for (UserFlagManager userFlagManager : userFlagManagers) {
            if (userFlagManager.getStatus() == FlagStatus.ACCEPT) {
                ret.addAll(userFlagManager.addAbleCellIndex());
            }
        }
        return ret;
    }

    // 모든 인원이 일정을 입력했다면 확정 가능
    public void checkState() {
        for (UserFlagManager userFlagManager : userFlagManagers) {
            if (userFlagManager.getStatus() == FlagStatus.STANDBY) {
                return;
            }
        }
        this.state = true;
    }

    public void fixFlag(LocalDate fixedDate, String startTime, String endTime, List<String> fixedMembers) {
        this.fixedDate = fixedDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fixedMembers = fixedMembers;
    }
}