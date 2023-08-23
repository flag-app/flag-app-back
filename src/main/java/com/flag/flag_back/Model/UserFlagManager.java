package com.flag.flag_back.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class UserFlagManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userFlagManagerId")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "flagId")
    private Flag flag;

    // 호스트, 게스트 여부의 enum
    @Enumerated(EnumType.STRING)
    private FlagRole role;

    // 수락, 대기 여부의 enum
    @Enumerated(EnumType.STRING)
    private FlagStatus status;

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private Day day;

    public UserFlagManager(Flag flag, User user, FlagRole role, FlagStatus status) {
        this.flag = flag;
        this.user = user;
        this.role = role;
        this.status = status;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void acceptFlag() {
        this.status = FlagStatus.ACCEPT;
    }

    public List<Integer> addAbleCellIndex() {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (this.ableOrNot(i)) {
                ret.add(i);
            }
        }
        return ret;
    }

    public boolean ableOrNot(int index) {
        return day.getSchedule(index);
    }
}