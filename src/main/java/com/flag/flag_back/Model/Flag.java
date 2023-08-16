package com.flag.flag_back.Model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data@Table(name = "FlagTB")
@Entity
@Getter
@Setter
public class Flag {
    @Id
    @Column(name = "flagId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flagName")
    private String name;
    @Column(name = "min")
    private Integer minTime;
    @Column(name = "place")
    private String place;
    @Column(name = "memo")
    private String memo;
    @Column(name = "state")
    private boolean state;

    @ElementCollection
    private List<String> dates;

    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL)
    private List<UserFlagManager> userFlagManagers = new ArrayList<>();

    @Builder
    public Flag(String name, Integer minTime, String place, String memo, boolean state, List<String> dates) {
        this.name = name;
        this.minTime = minTime;
        this.place = place;
        this.memo = memo;
        this.state = state;
        this.dates = dates;
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
        System.out.println("이거는 됨!");
        return ret;
    }

    public List<String> getNonResponseUsers() {
        List<String> ret = new ArrayList<>();
        System.out.println("여기서 에러터지는듯");
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
        for (UserFlagManager userFlagManager :userFlagManagers) {
            if (userFlagManager.getStatus() != FlagStatus.ACCEPT) {
                continue;
            }
            ret.addAll(userFlagManager.addAbleCellIndex());
        }
        return ret;
    }

    public boolean getState() {
        return state;
    }
}