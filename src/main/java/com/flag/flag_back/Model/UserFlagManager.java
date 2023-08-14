package com.flag.flag_back.Model;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class UserFlagManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userFlagManagerId")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "flagId")
    private Flag flag;

    // 호스트, 게스트 여부의 enum
    @Enumerated(EnumType.STRING)
    private FlagRole role;

    // 수락, 대기, 거절 여부의 enum
    @Enumerated(EnumType.STRING)
    private FlagStatus status;

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

    public boolean ableOrNot(int index) {
        return day.getSchedule(index);
    }
}