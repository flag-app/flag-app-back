package com.flag.flag_back.Model;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data@Table(name = "FlagTB")
@Entity@Getter
@Setter
public class Flag {
    @Id
    @Column(name = "flagId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flagName")
    private String name;
    @Column(name = "cycle")
    private String cycle;
    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Day> dayList;
    @Column(name = "min")
    private Integer minTime;
    @Column(name = "place")
    private String place;
    @Column(name = "memo")
    private String memo;
    @Column(name = "userId")
    private Long userId;
    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FlagMember> friendsList;
    @Column(name = "state")
    private boolean state;
    @Column(name = "fixedDate")
    private String fixedDate;
    @Builder
    public Flag(Long id,String name, String cycle, List<Day> dayList, Integer minTime, String place, String memo, Long userId, List<FlagMember> friendsList, boolean state, String fixedDate) {
        this.id = id;
        this.name = name;
        this.cycle = cycle;
        this.dayList = dayList;
        this.minTime = minTime;
        this.place = place;
        this.memo = memo;
        this.userId = userId;
        this.friendsList = friendsList;
        this.state = state;
        this.fixedDate = fixedDate;
    }
}